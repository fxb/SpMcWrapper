package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcClient;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;

public class SpMcChatHandler implements SpMcHandler, ChatColors {
	public Packet handleClientPacket(SpMcClient client, Packet packet){
		if(packet instanceof PacketChatMessage){
			
		}
		
		return packet;
	}
	
	public Packet handleServerPacket(SpMcClient client, Packet packet){
		if(packet instanceof PacketChatMessage){
			PacketChatMessage chat = (PacketChatMessage)packet;
			
			chat.message = COLOR_LIGHT_GREEN + "Spotify: " + COLOR_WHITE + chat.message;
			
			return chat;
		}
		
		return null;
	}
}
