package de.felixbruns.minecraft;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.felixbruns.minecraft.SpMcStorage.SpMcSettings;
import de.felixbruns.minecraft.handlers.CommandFinder;
import de.felixbruns.minecraft.handlers.CommandHandler;
import de.felixbruns.minecraft.protocol.Position;

public class SpMcWrapper extends Thread implements SpMcConsoleHandler {
	private SpMcSettings         settings;
	private SpMcMinecraftStarter starter;
	private ServerSocket         server;
	
	private Map<String, SpMcPlayer> players;
	private Map<String, SpMcGroup>  groups;
	private Map<String, Position>   warpPoints;
	
	public SpMcWrapper(boolean startServer) throws IOException {
		super("SpMcWrapper");
		
		/* Load settings. */
		this.settings = SpMcStorage.loadSettings();
		
		/* Initialize client and warp point maps. */
		this.players    = new HashMap<String, SpMcPlayer>();
		this.groups     = SpMcStorage.loadGroups();
		this.warpPoints = SpMcStorage.loadWarpPoints();
		
		/* Create and bind wrapper server. */
		this.server = new ServerSocket(
			this.settings.getWrapperPort(), 50,
			InetAddress.getByName(this.settings.getWrapperHost())
		);
		
		/* Start server or use existing one. */
		if(startServer){
			System.out.println("Starting local minecraft server...");
			
			this.starter = new SpMcMinecraftStarter(1024);
			
			this.starter.addHandler(this);
			this.starter.start();
		}
		else{
			System.out.format(
				"Using minecraft server @ %s:%d...",
				this.settings.getMinecraftHost(),
				this.settings.getMinecraftPort()
			);
		}
	}
    
	public void stopServer(){
		if(!this.isConsoleAvailable()){
			System.out.println("WARNING: Can't stop server, since console access is not available!");
			
			return;
		}
		
		this.starter.terminate();
	}
	
	public void restartServer(){
		if(!this.isConsoleAvailable()){
			System.out.println("WARNING: Can't restart server, since console access is not available!");
			
			return;
		}
		
		this.starter.terminate();
		
		this.starter = new SpMcMinecraftStarter(1024);
		
		this.starter.addHandler(this);
		this.starter.start();
	}
	
	public boolean isConsoleAvailable(){
		return this.starter != null;
	}
	
    public void handleConsole(String message){
    	System.out.println(message);
    }
	
	public SpMcSettings getSettings(){
    	return this.settings;
    }
	
	public Map<String, SpMcPlayer> getPlayers(){
		return this.players;
	}
	
	public Map<String, SpMcGroup> getGroups(){
		return this.groups;
	}
	
	public SpMcGroup getGroupForPlayer(String name){
		for(Entry<String, SpMcGroup> entry : this.groups.entrySet()){
			if(entry.getValue().containsPlayer(name)){
				return entry.getValue();
			}
		}
		
		SpMcGroup group = this.groups.get("default");
		
		group.addPlayer(name);
		
		SpMcStorage.saveGroups(this.groups);
		
		return group;
	}
	
	public Map<String, Position> getWarpPoints(){
		return this.warpPoints;
	}
	
	public void handleConnection(Socket clientSocket) throws IOException {
		/* Open a connection to the minecraft server. */
		Socket serverSocket = new Socket(
			this.settings.getMinecraftHost(),
			this.settings.getMinecraftPort()
		);
		
		/* Create a new player. */
		SpMcPlayer player = new SpMcPlayer(this, serverSocket, clientSocket);
		
		//player.addHandler(new DaylightPacketHandler());
		
		for(CommandHandler handler : CommandFinder.getCommandHandlers()){
			player.addHandler(handler);
		}
		
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
		SpMcWrapper wrapper = new SpMcWrapper(false);
		
		wrapper.start();
		wrapper.join();
	}
}
