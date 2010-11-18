package de.felixbruns.minecraft;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import de.felixbruns.minecraft.handlers.SpMcHandler;
import de.felixbruns.minecraft.protocol.PacketStream;
import de.felixbruns.minecraft.protocol.packets.Packet;

public class SpMcClient {
	private List<SpMcHandler> handlers;
	private PacketStream      serverStream;
	private PacketStream      clientStream;
	
	public String username;
	
	public SpMcClient(Socket serverSocket, Socket clientSocket) throws IOException {
		this.handlers      = new ArrayList<SpMcHandler>();
		this.serverStream  = new PacketStream(serverSocket);
		this.clientStream  = new PacketStream(clientSocket);
	}
	
	public void addHandler(SpMcHandler handler){
		synchronized(this.handlers){
			this.handlers.add(handler);
        }
	}
	
	public void sendToServer(Packet packet){
    	try {
	        this.serverStream.write(packet);
        }
        catch(IOException e){
        	/* Ignore. */
        }
    }
	
	public void sendToClient(Packet packet){
    	try {
	        this.clientStream.write(packet);
        }
        catch(IOException e){
        	/* Ignore. */
        }
    }
	
	public Packet handleClientPacket(Packet packet){
		synchronized(SpMcClient.this.handlers){
    		for(SpMcHandler handler : SpMcClient.this.handlers){
    			packet = handler.handleClientPacket(SpMcClient.this, packet);
    		}
        }
		
		return packet;
	}
	
	public Packet handleServerPacket(Packet packet){
		synchronized(SpMcClient.this.handlers){
    		for(SpMcHandler handler : SpMcClient.this.handlers){
    			packet = handler.handleServerPacket(SpMcClient.this, packet);
    		}
        }
		
		return packet;
	}
	
	public void disconnect(){
		this.serverStream.close();
		this.clientStream.close();
	}
	
	public void start(){
		new SpMcReadWriteThread(SpMcReadWriteThread.MODE_CLIENT_SERVER).start();
		new SpMcReadWriteThread(SpMcReadWriteThread.MODE_SERVER_CLIENT).start();
	}
	
	private class SpMcReadWriteThread extends Thread {
		public static final int MODE_CLIENT_SERVER = 0;
		public static final int MODE_SERVER_CLIENT = 1;
		
		private int          mode;
		private PacketStream input;
		private PacketStream output;
		
		public SpMcReadWriteThread(int mode){
			if(mode != MODE_CLIENT_SERVER && mode != MODE_SERVER_CLIENT){
				throw new IllegalArgumentException("Unknown mode given!");
			}
			
			this.mode = mode;
			
			if(this.mode == MODE_CLIENT_SERVER){
				this.input  = SpMcClient.this.clientStream;
				this.output = SpMcClient.this.serverStream;
			}
			else if(this.mode == MODE_SERVER_CLIENT){
				this.input  = SpMcClient.this.serverStream;
				this.output = SpMcClient.this.clientStream;
			}
		}
		
		public void run(){
			Packet packet;
			
			while(true){
				try {
		    		packet = this.input.read();
		    				    		
	    			if(this.mode == MODE_CLIENT_SERVER){
	    				SpMcClient.this.handleClientPacket(packet);
	    			}
	    			else if(this.mode == MODE_SERVER_CLIENT){
	    				SpMcClient.this.handleServerPacket(packet);
	    			}
		    		
		    		this.output.write(packet);
		        }
		        catch(IOException e){
		        	System.err.println("Lost connection!");
		        	
		        	return;
		        }
			}
		}
	}
}
