package de.felixbruns.minecraft;

import java.util.ArrayList;
import java.util.List;

import de.felixbruns.minecraft.handlers.CommandFinder;
import de.felixbruns.minecraft.protocol.PacketFinder;
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
		this.commands = new ArrayList<String>(CommandFinder.getCommands());
		this.players  = new ArrayList<String>();
		
		for(Class<? extends Packet> clazz : PacketFinder.getPacketClasses()){
			ProtocolPacket protocolPacket = clazz.getAnnotation(ProtocolPacket.class);
			
			if(protocolPacket.direction().isClientPacket()){
				this.packets.add(protocolPacket.name());
			}
		}
	}
	
	public String getPrefix(){
		return this.prefix;
	}
	
	public void addPlayer(String name){
		this.players.add(name);
	}
	
	public void removePlayer(String name){
		this.players.remove(name);
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
