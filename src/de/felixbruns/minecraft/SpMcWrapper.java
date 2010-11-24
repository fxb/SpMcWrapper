package de.felixbruns.minecraft;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import de.felixbruns.minecraft.SpMcStorage.SpMcSettings;
import de.felixbruns.minecraft.handlers.SpMcAdminCommandHandler;
import de.felixbruns.minecraft.handlers.SpMcDaylightHandler;
import de.felixbruns.minecraft.handlers.SpMcGenericCommandHandler;
import de.felixbruns.minecraft.handlers.SpMcWarpPointHandler;
import de.felixbruns.minecraft.protocol.Position;

public class SpMcWrapper extends Thread implements SpMcConsoleHandler {
	private SpMcSettings         settings;
	private SpMcMinecraftStarter starter;
	private ServerSocket         server;
	
	private Map<String, SpMcPlayer> players;
	private Map<String, Position>   warpPoints;
	
	public SpMcWrapper() throws IOException {
		super("SpMcWrapper");
		
		/* Load settings. */
		this.settings = SpMcStorage.loadSettings();
		
		/* Initialize client and warp point maps. */
		this.players    = new HashMap<String, SpMcPlayer>();
		this.warpPoints = SpMcStorage.loadWarpPoints();
		
		/* Create and bind wrapper server. */
		this.server = new ServerSocket(
			this.settings.getWrapperPort(), 50,
			InetAddress.getByName(this.settings.getWrapperHost())
		);
		
		/* Start the minecraft server. */
		this.starter = new SpMcMinecraftStarter(1024);
		
		this.starter.addHandler(this);
		this.starter.start();
	}
    
	public void stopServer(){
		this.starter.terminate();
	}

	public void restartServer(){
		this.starter.terminate();
		
		this.starter = new SpMcMinecraftStarter(1024);
		
		this.starter.addHandler(this);
		this.starter.start();
	}
	
	public Map<String, SpMcPlayer> getPlayers(){
		return this.players;
	}
	
	public Map<String, Position> getWarpPoints(){
		return this.warpPoints;
	}
	
    public void handleConsole(String message){
    	System.out.println(message);
    }
	
	public void handleConnection(Socket clientSocket) throws IOException {
		/* Open a connection to the minecraft server. */
		Socket serverSocket = new Socket(
			this.settings.getMinecraftHost(),
			this.settings.getMinecraftPort()
		);
		
		/* Create a new player. */
		SpMcPlayer player = new SpMcPlayer(this, serverSocket, clientSocket);
		
		player.addHandler(new SpMcWarpPointHandler());
		player.addHandler(new SpMcDaylightHandler());
		player.addHandler(new SpMcAdminCommandHandler());
		player.addHandler(new SpMcGenericCommandHandler());
		
        player.start();
	}
	
	public void run(){
		/* Wait for clients to connect, then handle them. */
		while(true){
			try {
				this.handleConnection(this.server.accept());
            }
            catch(IOException e){
	            e.printStackTrace();
            }
		}
	}
	
	public static void main(String[] args) throws Exception {
		SpMcWrapper wrapper = new SpMcWrapper();
		
		wrapper.start();
		wrapper.join();
	}
}
