package de.felixbruns.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.felixbruns.minecraft.protocol.Packets;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

public class SpMcGroup {
	private String       prefix;
	private List<String> packets;
	private List<String> commands;
	private List<String> players;
	
	public static final SpMcGroup DEFAULT = new SpMcGroup();
	
	private SpMcGroup(){
		this.prefix   = "";
		this.packets  = new ArrayList<String>();
		this.commands = new ArrayList<String>();
		this.players  = new ArrayList<String>();
		
		for(Entry<Integer, Class<? extends Packet>> packet : Packets.PACKETS.entrySet()){
			ProtocolPacket protocolPacket = packet.getValue().getAnnotation(ProtocolPacket.class);
			
			if(protocolPacket.type().contains("Client")){
				this.packets.add(protocolPacket.name());
			}
		}
		
		this.commands.add("*");
	}
	
	public String getPrefix(){
		return this.prefix;
	}
	
	public void addPlayer(String name){
		this.players.add(name);
	}
	
	public boolean containsPlayer(String name){
		return this.players.contains(name);
	}
	
	public boolean isPacketAllowed(Packet packet){
		ProtocolPacket protocolPacket = packet.getClass().getAnnotation(ProtocolPacket.class);
		
		if(this.packets.contains("*")){
			return true;
		}
		
		return this.packets.contains(protocolPacket.name());
	}
	
	public boolean isCommandAllowed(String command){
		if(this.commands.contains("*")){
			return true;
		}
		
		return this.commands.contains(command);
	}
}