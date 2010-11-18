package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcClient;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPosition;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPositionAndLook;

public class SpMcChatHandler implements SpMcHandler, ChatColors {
	public Packet handleClientPacket(SpMcClient client, Packet packet){
		if(packet instanceof PacketChatMessage){
			PacketChatMessage chat = (PacketChatMessage)packet;
			
			if(chat.message.contains("teleport")){
				PacketPlayerPositionAndLook pos = new PacketPlayerPositionAndLook();
				
				pos.x = 10.0;
				pos.z = 10.0;
				pos.yaw = 0.0f;
				pos.pitch = 0.0f;
				pos.onGround = false;
				pos.y = 128.0;
				pos.stance = 129.62;
				
				client.sendToServer(pos);
				
				client.sendToClient(pos);
			}
			
			return chat;
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
