package de.felixbruns.minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SpMcSettings {
	private File       file;
	private JSONObject data;
	
	private static final String SETTINGS_FILENAME = "settings.json";
	
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
}
