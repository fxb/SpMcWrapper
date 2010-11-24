package de.felixbruns.minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import de.felixbruns.minecraft.protocol.Position;

public class SpMcStorage {	
	private static final String STORAGE_SETTINGS    = "settings.json";
	private static final String STORAGE_WARP_POINTS = "warp-points";
	
	private static final Gson gson;
	
	static {
		gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create();
	}
	
	public static SpMcSettings loadSettings(){
		SpMcSettings settings = new SpMcSettings();
		
		try {
			settings = gson.fromJson(new FileReader(STORAGE_SETTINGS), SpMcSettings.class);
		}
		catch(IOException e){
			/* Ignore. */
		}
		catch(JsonParseException e){
			/* Ignore. */
		}
		
		return settings;
	}
	
	public static void saveSettings(SpMcSettings settings){
		try {
			FileWriter writer = new FileWriter(STORAGE_SETTINGS);
			
			gson.toJson(settings, writer);
			
			writer.close();
        }
		catch(IOException e){
			/* Ignore. */
		}
	}
	
	public static Map<String, Position> loadWarpPoints(){
		return loadWarpPoints("__global__");
	}
	
	public static Map<String, Position> loadWarpPoints(String username){
		Type                  type       = new TypeToken<HashMap<String, Position>>(){}.getType();
		Map<String, Position> warpPoints = new HashMap<String, Position>();
		
		try {
			warpPoints = gson.fromJson(new FileReader(
				STORAGE_WARP_POINTS + "/" + username + ".json"
			), type);
		}
		catch(IOException e){
			/* Ignore. */
		}
		catch(JsonParseException e){
			/* Ignore. */
		}
		
		return warpPoints;
	}
	
	public static void saveWarpPoints(Map<String, Position> warpPoints){
		saveWarpPoints("__global__", warpPoints);
	}
	
	public static void saveWarpPoints(String username, Map<String, Position> warpPoints){
		try {
			new File(STORAGE_WARP_POINTS).mkdirs();
			
			FileWriter writer = new FileWriter(STORAGE_WARP_POINTS + "/" + username + ".json");
			
			gson.toJson(warpPoints, writer);
			
			writer.close();
		}
		catch(IOException e){
			/* Ignore. */
		}
	}
	
	public static class SpMcSettings {
    	private String minecraftHost;
    	private int    minecraftPort;
    	private String wrapperHost;
    	private int    wrapperPort;
    	private String motd;
    	
		public SpMcSettings(){
	        this.minecraftHost = "localhost";
	        this.minecraftPort = 25565;
	        this.wrapperHost   = "localhost";
	        this.wrapperPort   = 25566;
	        this.motd          = "Welcome! You're connected through SpMcWrapper.";
        }
		
		public String getMinecraftHost(){
        	return this.minecraftHost;
        }
		
		public void setMinecraftHost(String host){
        	this.minecraftHost = host;
        }
		
		public int getMinecraftPort(){
        	return this.minecraftPort;
        }
		
		public void setMinecraftPort(int port){
        	this.minecraftPort = port;
        }
		
		public String getWrapperHost(){
        	return this.wrapperHost;
        }
		
		public void setWrapperHost(String host){
        	this.wrapperHost = host;
        }
		
		public int getWrapperPort(){
        	return this.wrapperPort;
        }
		
		public void setWrapperPort(int port){
        	this.wrapperPort = port;
        }
		
		public String getMotd(){
        	return this.motd;
        }
		
		public void setMotd(String motd){
        	this.motd = motd;
        }
	}
}
