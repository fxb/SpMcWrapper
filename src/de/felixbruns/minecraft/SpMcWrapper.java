package de.felixbruns.minecraft;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import de.felixbruns.minecraft.handlers.SpMcChatHandler;
import de.felixbruns.minecraft.handlers.SpMcLoginHandler;

public class SpMcWrapper extends Thread implements SpMcConsoleHandler {
	private String       host;
	private int          port;
	private ServerSocket wrapper;
	
	public SpMcWrapper(String host, int port, String wrapperHost, int wrapperPort) throws IOException {
		this.host    = host;
		this.port    = port;
		
		/* Create and bind wrapper server. */
		this.wrapper = new ServerSocket(wrapperPort, 50, InetAddress.getByName(wrapperHost));
		
		/* Start the minecraft server. */
		SpMcServer server = new SpMcServer(1024);
		
		server.addHandler(this);
		server.start();
	}
    
    public void handleConsole(String message){
    	System.out.println(message);
    }
	
	public void handleClient(Socket clientSocket) throws IOException {
		/* Open a connection to the minecraft server. */
		Socket serverSocket = new Socket(this.host, this.port);
		
		/* Create a client handler and add it to the map of users. */
		SpMcClient client = new SpMcClient(serverSocket, clientSocket);
		
		//client.addHandler(new SpMcCommandHandler());
		client.addHandler(new SpMcLoginHandler());
		client.addHandler(new SpMcChatHandler());
		
        client.start();
	}
	
	public void run(){
		/* Wait for clients to connect, then handle them. */
		while(true){
			try {
				this.handleClient(this.wrapper.accept());
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
