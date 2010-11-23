package de.felixbruns.minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import de.felixbruns.minecraft.protocol.Position;

public class SpMcSettings {
	private File       file;
	private JSONObject data;
	
	private static final String SETTINGS_FILENAME     = "settings.json";
	private static final String WARP_POINTS_DIRECTORY = "warp-points";
	
	private static SpMcSettings instance;
	
	public static SpMcSettings getInstance(){
		if(instance == null){
			try {
	            instance = new SpMcSettings();
            }
			catch (IOException e){
				e.printStackTrace();
				
				return null;
			}
            catch(JSONException e){
				e.printStackTrace();
				
            	return null;
            }
		}
		
		return instance;
	}
	
	public SpMcSettings() throws FileNotFoundException, JSONException {
		this.file = new File(SETTINGS_FILENAME);
		
		this.load();
		
		if(this.data == null || this.data.length() == 0){
			this.create();
			this.save();
		}
	}
	
	public String getString(String key, String defaultValue){
		return this.data.optString(key, defaultValue);
	}
	
	public String getString(String key){
		return this.data.optString(key, "");
	}
	
	public int getInt(String key, int defaultValue){
		return this.data.optInt(key, defaultValue);
	}
	
	public int getInt(String key){
		return this.data.optInt(key, 0);
	}
	
	public void create(){
		try {
			this.data = new JSONObject()
    			.put("minecraft-host", "localhost")
    			.put("minecraft-port", 25565)
    			.put("wrapper-host", "localhost")
    			.put("wrapper-port", 25566)
    			.put("motd", "Welcome to this Minecraft Server!")
    		;
        }
        catch(JSONException e){
        	/* Ignore. */
        }
	}
	
	public void load(){
		try {
			this.data = new JSONObject(new JSONTokener(new FileReader(this.file)));
        }
        catch(IOException e){
	        /* Ignore. */
        }
        catch(JSONException e){
        	/* Ignore. */
        }
	}
	
	public void save(){
		try {
			FileWriter writer = new FileWriter(this.file);
			
			writer.write(this.data.toString(4));
			writer.close();
        }
        catch(IOException e){
	        /* Ignore. */
        }
        catch(JSONException e){
        	/* Ignore. */
        }
	}
	
	public static Map<String, Position> loadWarpPoints(){
		return loadWarpPoints("__global__");
	}
	
	public static Map<String, Position> loadWarpPoints(String username){
		Map<String, Position> warpPoints = new HashMap<String, Position>();
		
		try {
			JSONArray data = new JSONArray(new JSONTokener(
				new FileReader(WARP_POINTS_DIRECTORY + "/" + username + ".json"))
			);
			
			for(int i = 0; i < data.length(); i++){
				JSONObject point    = data.getJSONObject(i);
				String     name     = point.getString("name");
				JSONObject position = point.getJSONObject("position");
				
				double x = position.getDouble("x");
				double y = position.getDouble("y");
				double z = position.getDouble("z");
				
				warpPoints.put(name, new Position(x, y, z));
			}
		}
		catch(IOException e){
			/* Ignore. */
		}
		catch(JSONException e){
			/* Ignore. */
		}
		
		return warpPoints;
	}
	
	public static void saveWarpPoints(Map<String, Position> warpPoints){
		saveWarpPoints("__global__", warpPoints);
	}
	
	public static void saveWarpPoints(String username, Map<String, Position> warpPoints){
		try {
			new File(WARP_POINTS_DIRECTORY).mkdirs();
			
			FileWriter writer = new FileWriter(WARP_POINTS_DIRECTORY + "/" + username + ".json");
			JSONWriter data   = new JSONWriter(writer);
			
			data.array();
			
			for(Entry<String, Position> entry : warpPoints.entrySet()){
				String   name     = entry.getKey();
				Position position = entry.getValue();
				
				data.object()
					.key("name").value(name)
					.key("position").object()
						.key("x").value(position.x)
						.key("y").value(position.y)
						.key("z").value(position.z)
					.endObject()
				.endObject();
			}
			
			data.endArray();
			
			writer.close();
		}
		catch(IOException e){
			/* Ignore. */
		}
		catch(JSONException e){
			/* Ignore. */
		}
	}
}
