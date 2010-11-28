package de.felixbruns.minecraft;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.felixbruns.minecraft.handlers.PacketHandler;
import de.felixbruns.minecraft.protocol.PacketStream;
import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketBlockChange;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;
import de.felixbruns.minecraft.protocol.packets.PacketDisconnect;
import de.felixbruns.minecraft.protocol.packets.PacketLogin;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerBlockPlacement;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerDigging;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPosition;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPositionAndLook;

public class SpMcPlayer {
	private SpMcWrapper         wrapper;
	private PacketStream        serverStream;
	private PacketStream        clientStream;
	private List<PacketHandler> handlers;
	
	private String                name;
	private int                   eid;
	private Position              position;
	private Map<String, Position> warpPoints;
	private SpMcGroup             group;
	
	/**
	 * Create a new player.
	 * 
	 * @param wrapper      The associated wrapper.
	 * @param serverSocket The socket to use as the server.
	 * @param clientSocket The socket to use as the client.
	 * 
	 * @throws IOException If an I/O error occurs while getting streams from the sockets.
	 */
	public SpMcPlayer(SpMcWrapper wrapper, Socket serverSocket, Socket clientSocket) throws IOException {
		this.wrapper       = wrapper;
		this.serverStream  = new PacketStream(serverSocket);
		this.clientStream  = new PacketStream(clientSocket);
		this.handlers      = new ArrayList<PacketHandler>();
		
		this.name       = null;
		this.eid        = -1;
		this.position   = new Position();
		this.warpPoints = new HashMap<String, Position>();
		this.group      = null;
	}
	
	/**
	 * Get a reference to the wrapper, which will
	 * provide access to global lists and maps.
	 * 
	 * @return The associated wrapper.
	 */
	public SpMcWrapper getWrapper(){
		return this.wrapper;
	}
	
	/**
	 * Get the associated name.
	 * 
	 * @return The players name.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Get the display name including the group prefix and possible colors.
	 * 
	 * @return The players display name.
	 */
	public String getDisplayName(){
		if(this.group != null){
			return this.group.getPrefix() + this.name;
		}
		
		return this.name;
	}
	
	/**
	 * Get the associated entity id.
	 * 
	 * @return The players entity id.
	 */
	public int getEid(){
		return this.eid;
	}
	
	/**
	 * Get the current in-game position.
	 * 
	 * @return The position of the player.
	 */
	public Position getPosition(){
		return this.position;
	}
	
	/**
	 * Get the user specific map of warps points.
	 * 
	 * @return The map of warp points.
	 */
	public Map<String, Position> getWarpPoints(){
		return this.warpPoints;
	}
	
	public SpMcGroup getGroup(){
		return this.group;
	}
	
	public void setGroup(SpMcGroup group){
		this.group = group;
	}
	
	/**
	 * Add a handler for handling packets.
	 * 
	 * @param handler The handler to add.
	 */
	public void addHandler(PacketHandler handler){
		synchronized(this.handlers){
			this.handlers.add(handler);
        }
	}
	
	/**
	 * Send a packet to the server associated with this player.
	 * 
	 * @param packet The packet to send.
	 */
	public void sendToServer(Packet packet){
    	try {
	        this.serverStream.write(packet);
        }
        catch(IOException e){
        	/* Ignore. */
        }
    }
	
	/**
	 * Send a packet to the client associated with this player.
	 * 
	 * @param packet The packet to send.
	 */
	public void sendToClient(Packet packet){
    	try {
	        this.clientStream.write(packet);
        }
        catch(IOException e){
        	/* Ignore. */
        }
    }
	
	/**
	 * Send a chat message to the client associated with this player.
	 * 
	 * @param message The chat message to send.
	 */
	public void sendMessage(String message){
    	this.sendToClient(new PacketChatMessage(message));
    }
	
	/**
	 * Send a chat message to the client associated with this player.
	 * 
	 * @param color  The desired.
	 * @param format The format.
	 * @param args   The format arguments.
	 */
	public void sendMessage(String color, String format, Object... args){
    	this.sendToClient(new PacketChatMessage(color + String.format(format, args)));
    }
	
	/**
	 * Handles a packet from the client.
	 * 
	 * @param packet The packet to handle.
	 * 
	 * @return The handled packet or {@code null} if it shall be dropped.
	 */
	private Packet handleClientPacket(Packet packet){
		/* Get player username from login packet. */
		if(packet instanceof PacketLogin){
			PacketLogin login = (PacketLogin)packet;
			
			this.name = login.username;
		}
		/* Remove client from wrapper on disconnect. */
		else if(packet instanceof PacketDisconnect){
			this.wrapper.getPlayers().remove(this.name);
		}
		/* Keep track of the players position. */
		else if(packet instanceof PacketPlayerPosition){
			this.position = ((PacketPlayerPosition)packet).getPosition();
		}
		else if(packet instanceof PacketPlayerPositionAndLook){
			this.position = ((PacketPlayerPositionAndLook)packet).getPosition();
		}
		else if(packet instanceof PacketPlayerDigging && this.group != null && !this.group.isPacketAllowed(packet)){
			PacketPlayerDigging digging = ((PacketPlayerDigging)packet);
			
			if(digging.status == 3){				
				PacketBlockChange change = new PacketBlockChange();
				
				change.x        = digging.x;
				change.y        = digging.y;
				change.z        = digging.z;
				change.type     = 49;
				change.metadata = 0;
				
				this.sendToClient(change);
			}
			
			return null;
		}
		else if(packet instanceof PacketPlayerBlockPlacement && this.group != null && !this.group.isPacketAllowed(packet)){
			PacketPlayerBlockPlacement placement = ((PacketPlayerBlockPlacement)packet);
			PacketBlockChange          change    = new PacketBlockChange();
			
			change.x        = placement.x;
			change.y        = placement.y;
			change.z        = placement.z;
			change.type     = 0;
			change.metadata = 0;
			
			if(placement.direction == 0){
				change.y--;
			}
			else if(placement.direction == 1){
				change.y++;
			}
			else if(placement.direction == 2){
				change.z--;
			}
			else if(placement.direction == 3){
				change.z++;
			}
			else if(placement.direction == 4){
				change.x--;
			}
			else if(placement.direction == 5){
				change.x++;
			}
			
			this.sendToClient(change);
			
			return null;
		}
		
		/* Check if packet is allowed for user. */
		if(this.group != null && !this.group.isPacketAllowed(packet)){
			return null;
		}
		
		/* Notify any external handlers. */
		synchronized(this.handlers){
    		for(PacketHandler handler : this.handlers){
    			packet = handler.handleClientPacket(SpMcPlayer.this, packet);
    			
    			if(packet == null){
    				return packet;
    			}
    		}
        }
		
		return packet;
	}
	
	/**
	 * Handles a packet from the server.
	 * 
	 * @param packet The packet to handle.
	 * 
	 * @return The handled packet or {@code null} if it shall be dropped.
	 */
	private Packet handleServerPacket(Packet packet){
		/* Get player entity id from login packet. */
		if(packet instanceof PacketLogin){			
			PacketLogin login = (PacketLogin)packet;
			
			this.eid = login.versionOrEid;
			
			this.wrapper.getPlayers().put(this.name, this);
			
			this.group      = this.wrapper.getGroupForPlayer(this.name);
			this.warpPoints = SpMcStorage.loadWarpPoints(this.name);
		}
		/* Remove client from wrapper on disconnect. */
		else if(packet instanceof PacketDisconnect){
			this.wrapper.getPlayers().remove(this.name);
		}
		/* Keep track of the players position. */
		else if(packet instanceof PacketPlayerPositionAndLook){
			this.position = ((PacketPlayerPositionAndLook)packet).getPosition();
		}
		else if(packet instanceof PacketChatMessage){
			PacketChatMessage chat = (PacketChatMessage)packet;
			
			if(chat.message.matches("<(.+)> (.+)")){
				String     name    = chat.message.replaceAll("<(.+)> (.+)", "$1");
				String     message = chat.message.replaceAll("<(.+)> (.+)", "$2");
				SpMcPlayer player  = this.wrapper.getPlayers().get(name);
				
				chat.message = String.format(
					"%s:§f %s", player.getDisplayName(), message
				);
			}
			if(chat.message.matches("§e(.+) joined the game.")){
				String     name  = chat.message.replaceAll("§e(.+) joined the game.", "$1");
				SpMcPlayer player  = this.wrapper.getPlayers().get(name);
				
				chat.message = String.format(
					"%s§e joined the game.", player.getDisplayName()
				);
			}
		}
		
		/* Notify any external handlers. */
		synchronized(this.handlers){
    		for(PacketHandler handler : this.handlers){
    			packet = handler.handleServerPacket(SpMcPlayer.this, packet);
    			
    			if(packet == null){
    				return packet;
    			}
    		}
        }
		
		return packet;
	}
	
	/**
	 * Start read-write threads which pass and handle packets in both directions.
	 */
	public void start(){
		new SpMcReadWriteThread(SpMcReadWriteThread.DIRECTION_CLIENT_SERVER).start();
		new SpMcReadWriteThread(SpMcReadWriteThread.DIRECTION_SERVER_CLIENT).start();
	}
	
	/**
	 * Disconnect both, the server and the client side of this player.
	 */
	public void disconnect(){
		this.serverStream.close();
		this.clientStream.close();
	}
	
	/**
	 * Reads packets from the server and passes them to the client
	 * or the other way around. Packets will be passed to handlers,
	 * which can modify, insert or drop packets.
	 */
	private class SpMcReadWriteThread extends Thread {
		public static final int DIRECTION_CLIENT_SERVER = 0;
		public static final int DIRECTION_SERVER_CLIENT = 1;
		
		private int          direction;
		private PacketStream input;
		private PacketStream output;
		
		/**
		 * Create a new read-write thread using the specified direction.
		 * 
		 * @param direction One of {@link SpMcReadWriteThread#DIRECTION_CLIENT_SERVER} or
		 *                  {@link SpMcReadWriteThread#DIRECTION_SERVER_CLIENT}.
		 */
		public SpMcReadWriteThread(int direction){
			super("SpMcReadWriteThread");
			
			if(direction != DIRECTION_CLIENT_SERVER && direction != DIRECTION_SERVER_CLIENT){
				throw new IllegalArgumentException("Unknown mode given!");
			}
			
			this.direction = direction;
			
			if(this.direction == DIRECTION_CLIENT_SERVER){
				this.input  = SpMcPlayer.this.clientStream;
				this.output = SpMcPlayer.this.serverStream;
			}
			else if(this.direction == DIRECTION_SERVER_CLIENT){
				this.input  = SpMcPlayer.this.serverStream;
				this.output = SpMcPlayer.this.clientStream;
			}
		}
		
		public void run(){
			Packet packet;
			
			while(true){
				try {
		    		packet = this.input.read();
		    		
	    			if(this.direction == DIRECTION_CLIENT_SERVER){
	    				packet = SpMcPlayer.this.handleClientPacket(packet);
	    			}
	    			else if(this.direction == DIRECTION_SERVER_CLIENT){
	    				packet = SpMcPlayer.this.handleServerPacket(packet);
	    			}
		    		
	    			if(packet != null){
	    				this.output.write(packet);
	    			}
		        }
		        catch(IOException e){
					SpMcPlayer.this.wrapper.getPlayers().remove(SpMcPlayer.this.name);
					
		        	System.err.println("Lost connection to " + SpMcPlayer.this.name + "!");
		        	
		        	return;
		        }
			}
		}
	}
}
