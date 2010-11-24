package de.felixbruns.minecraft.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonPrettyPrint {
	private final Gson gson;
	private final int  spaces;
	
	public GsonPrettyPrint(Gson gson){
		this(gson, 4);
	}
	
	public GsonPrettyPrint(Gson gson, int spaces){
		this.gson   = gson;
		this.spaces = spaces;
	}
	
	public String toJson(Object src){
		return this.toJson(src, (Type)null);
	}
	
	public String toJson(Object src, Type typeOfSrc){
		StringWriter writer = new StringWriter();
		
		this.toJson(src, null, writer);
		
		return writer.toString();
	}
	
	public void toJson(Object src, Appendable writer){
		this.toJson(src, null, writer);
	}
	
	public void toJson(Object src, Type typeOfSrc, Appendable writer){
		if(typeOfSrc == null){
			typeOfSrc = src.getClass();
		}
		
		try {
			this.toJson(src, writer, typeOfSrc);
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public void toJson(Object src, Appendable writer, Type typeOfSrc) throws IOException {
		JsonElement element = this.gson.toJsonTree(src, typeOfSrc);
		
		this.toJson(element, writer, 0);
		
		writer.append("\n");
	}
	
	private void toJson(JsonArray array, Appendable writer, int level) throws IOException {
		if(array.size() == 0){
			writer.append("[]");
			
			return;
		}
		
		writer.append("[\n");
		
		Iterator<JsonElement> it = array.iterator();
		
		while(it.hasNext()){
			this.indent(writer, level + 1);
			
			this.toJson(it.next(), writer, level + 1);
			
			if(it.hasNext()){
				writer.append(",\n");
			}
			else{
				writer.append("\n");
			}
		}
		
		this.indent(writer, level);
		
		writer.append("]");
	}

	private void toJson(JsonObject object, Appendable writer, int level) throws IOException {
		int n = object.entrySet().size();
		int i = 0;
		
		if(n == 0){
			writer.append("{}");
			
			return;
		}
		
		writer.append("{\n");
		
		for(Entry<String, JsonElement> entry : object.entrySet()){
			this.indent(writer, level + 1);
			
			writer.append("\"" + entry.getKey() + "\" : ");
			
			this.toJson(entry.getValue(), writer, level + 1);
			
			if(++i < n){
				writer.append(",\n");
			}
			else{
				writer.append("\n");
			}
		}
		
		this.indent(writer, level);
		
		writer.append("}");
	}
	
	private void toJson(JsonElement element, Appendable writer, int level) throws IOException {
		if(element == null || element.isJsonNull()){
			writer.append("null");
		}
		else if(element.isJsonPrimitive()){
			writer.append(element.getAsJsonPrimitive().toString());
		}
		else if(element.isJsonArray()){
			this.toJson(element.getAsJsonArray(), writer, level);
		}
		else if (element.isJsonObject()) {
			this.toJson(element.getAsJsonObject(), writer, level);
		}
		else{
			throw new IllegalArgumentException("Invalid JsonElement '" + element.getClass() + "'!");
		}
	}
	
	private void indent(Appendable writer, int level) throws IOException {
		for(int i = 0; i < this.spaces * level; i++){
			writer.append(' ');
		}
	}
}