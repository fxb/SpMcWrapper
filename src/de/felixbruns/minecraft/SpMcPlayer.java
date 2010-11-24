package de.felixbruns.minecraft;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.felixbruns.minecraft.handlers.SpMcHandler;
import de.felixbruns.minecraft.protocol.PacketStream;
import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;
import de.felixbruns.minecraft.protocol.packets.PacketDisconnect;
import de.felixbruns.minecraft.protocol.packets.PacketLogin;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPosition;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPositionAndLook;

public class SpMcPlayer {
	private SpMcWrapper       wrapper;
	private PacketStream      serverStream;
	private PacketStream      clientStream;
	private List<SpMcHandler> handlers;
	
	private String                username;
	private int                   eid;
	private Position              position;
	private Map<String, Position> warpPoints;
	
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
		this.handlers      = new ArrayList<SpMcHandler>();
		
		this.username   = null;
		this.eid        = -1;
		this.position   = new Position();
		this.warpPoints = new HashMap<String, Position>();
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
	 * Get the associated username.
	 * 
	 * @return The players username.
	 */
	public String getUsername(){
		return this.username;
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
	
	/**
	 * Add a handler for handling packets.
	 * 
	 * @param handler The handler to add.
	 */
	public void addHandler(SpMcHandler handler){
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
			
			this.username = login.username;
		}
		/* Remove client from wrapper on disconnect. */
		else if(packet instanceof PacketDisconnect){
			this.wrapper.getPlayers().remove(this.username);
		}
		/* Keep track of the players position. */
		else if(packet instanceof PacketPlayerPosition){
			this.position = ((PacketPlayerPosition)packet).getPosition();
		}
		else if(packet instanceof PacketPlayerPositionAndLook){
			this.position = ((PacketPlayerPositionAndLook)packet).getPosition();
		}
		
		/* Notify any external handlers. */
		synchronized(this.handlers){
    		for(SpMcHandler handler : this.handlers){
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
			
			this.wrapper.getPlayers().put(this.username, this);
			
			this.warpPoints = SpMcStorage.loadWarpPoints(this.username);
		}
		/* Remove client from wrapper on disconnect. */
		else if(packet instanceof PacketDisconnect){
			this.wrapper.getPlayers().remove(this.username);
		}
		/* Keep track of the players position. */
		else if(packet instanceof PacketPlayerPositionAndLook){
			this.position = ((PacketPlayerPositionAndLook)packet).getPosition();
		}
		
		/* Notify any external handlers. */
		synchronized(this.handlers){
    		for(SpMcHandler handler : this.handlers){
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
					SpMcPlayer.this.wrapper.getPlayers().remove(SpMcPlayer.this.username);
					
		        	System.err.println("Lost connection to " + SpMcPlayer.this.username + "!");
		        	
		        	return;
		        }
			}
		}
	}
}
