package de.felixbruns.minecraft;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import de.felixbruns.minecraft.handlers.SpMcDaytimeHandler;
import de.felixbruns.minecraft.handlers.SpMcWarpPointHandler;
import de.felixbruns.minecraft.protocol.Position;

public class SpMcWrapper extends Thread implements SpMcConsoleHandler {
	private String       host;
	private int          port;
	private ServerSocket wrapper;
	
	private Map<String, SpMcPlayer> players;
	private Map<String, Position>   warpPoints;
	
	public SpMcWrapper(String host, int port, String wrapperHost, int wrapperPort) throws IOException {
		this.host = host;
		this.port = port;
		
		/* Initialize client and warp point maps. */
		this.players    = new HashMap<String, SpMcPlayer>();
		this.warpPoints = new HashMap<String, Position>();
		
		/* Create and bind wrapper server. */
		this.wrapper = new ServerSocket(wrapperPort, 50, InetAddress.getByName(wrapperHost));
		
		/* Start the minecraft server. */
		SpMcMinecraftStarter server = new SpMcMinecraftStarter(1024);
		
		server.addHandler(this);
		server.start();
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
		Socket serverSocket = new Socket(this.host, this.port);
		
		/* Create a new player. */
		SpMcPlayer player = new SpMcPlayer(this, serverSocket, clientSocket);
		
		player.addHandler(new SpMcWarpPointHandler());
		player.addHandler(new SpMcDaytimeHandler());
		
        player.start();
	}
	
	public void run(){
		/* Wait for clients to connect, then handle them. */
		while(true){
			try {
				this.handleConnection(this.wrapper.accept());
            }
            catch(IOException e){
	            e.printStackTrace();
            }
		}
	}
	
	public static void main(String[] args) throws Exception {
		SpMcSettings settings = SpMcSettings.getInstance();
		SpMcWrapper  wrapper  = new SpMcWrapper(
			settings.getString("minecraft-host", "localhost"),
			settings.getInt("minecraft-port", 25565),
			settings.getString("wrapper-host", "localhost"),
			settings.getInt("wrapper-port", 25566)
		);
		
		wrapper.start();
		wrapper.join();
	}
}
