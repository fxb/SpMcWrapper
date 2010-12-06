package de.felixbruns.minecraft;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.util.GsonPrettyPrint;

public class SpMcHttpServer implements HttpHandler {
	private static final String PATH_PLAYERS     = "/players";
	private static final String PATH_WARP_POINTS = "/warps";
	
	private static final GsonPrettyPrint gsonPrettyPrint;
	
	private SpMcWrapper wrapper;
	private HttpServer  server;
	
	static {
		gsonPrettyPrint = new GsonPrettyPrint(new Gson());
	}
	
	public SpMcHttpServer(SpMcWrapper wrapper) throws IOException {
		this.wrapper = wrapper;
		this.server  = HttpServer.create(new InetSocketAddress(8080), 0);
		
		this.server.createContext(PATH_PLAYERS, this);
		this.server.createContext(PATH_WARP_POINTS, this);
	}
	
	public void start(){
		this.server.setExecutor(Executors.newCachedThreadPool());
		this.server.start();
	}
	
	public void stop(){
		this.server.stop(0);
	}
	
    public void handle(HttpExchange exchange) throws IOException {
    	String       method   = exchange.getRequestMethod();
    	String       path     = exchange.getRequestURI().getPath();
		Headers      headers  = exchange.getResponseHeaders();
		String       response = null;
		OutputStream body     = exchange.getResponseBody();
    	
    	if(method.equalsIgnoreCase("GET")){
    		headers.set("Content-Type", "application/javascript");
    		
    		if(path.equals(PATH_PLAYERS)){
        		Map<String, Position> players = new HashMap<String, Position>();
    			
        		for(Entry<String, SpMcPlayer> entry : this.wrapper.getPlayers().entrySet()){
        			players.put(entry.getKey(), entry.getValue().getPosition());
        		}
        		
        		response = gsonPrettyPrint.toJson(players);
    		}
    		else if(path.equals(PATH_WARP_POINTS)){
    			response = gsonPrettyPrint.toJson(this.wrapper.getWarpPoints());
    		}
    	}
    	
    	if(response != null){
        	byte[] data = response.getBytes(
        		Charset.forName("UTF-8")
        	);
        	
    		exchange.sendResponseHeaders(200, data.length);
    		
    		body.write(data);
    		body.close();
    	}
    	else{
    		exchange.sendResponseHeaders(404, -1);
    	}
    }
}
