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
		String         name           = protocolPacket.name();
		
		/* Check if packet is disallowed. */
		if(this.packets.contains("-" + name)){
			return false;
		}
		
		/* Check if packet is allowed. */
		if(this.packets.contains(name) || this.packets.contains("+" + name)){
			return true;
		}
		
		/* Check if all packets are allowed. */
		if(this.packets.contains("*")){
			return true;
		}
		
		return false;
	}
	
	public boolean isCommandAllowed(String command){
		/* Check if command is disallowed. */
		if(this.commands.contains("-" + command)){
			return false;
		}
		
		/* Check if command is allowed. */
		if(this.commands.contains(command) || this.commands.contains("+" + command)){
			return true;
		}
		
		/* Check if all packets are allowed. */
		if(this.commands.contains("*")){
			return true;
		}
		
		return false;
	}
}
