package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcClient;
import de.felixbruns.minecraft.SpMcSettings;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;
import de.felixbruns.minecraft.protocol.packets.PacketDisconnect;
import de.felixbruns.minecraft.protocol.packets.PacketLogin;

public class SpMcLoginHandler implements SpMcHandler, ChatColors {
	public Packet handleClientPacket(SpMcClient client, Packet packet){
		if(packet instanceof PacketLogin){
			PacketLogin login = (PacketLogin)packet;
			
			if(login.username.equals("admin")){
				PacketDisconnect p = new PacketDisconnect();
				
				p.reason = "Username not allowed!";
				
				client.sendToClient(p);
				
				client.disconnect();
			}
			
			client.username = login.username;
		}
		
		return packet;
	}
	
	public Packet handleServerPacket(SpMcClient client, Packet packet){
		if(packet instanceof PacketLogin){			
			PacketChatMessage message = new PacketChatMessage();
			
			message.message = SpMcSettings.getInstance().getString("motd");
			
			client.sendToClient(message);
		}
		
		return packet;
	}
}
