package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x03, direction = Direction.BIDIRECTIONAL, name = "ChatMessage")
public class PacketChatMessage extends Packet {
	@ProtocolField(name = "Message")
	public String message;
	
	public PacketChatMessage(){
		this("");
	}
	
	public PacketChatMessage(String message){
		this.message = message;
	}
}
