package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = (byte)0xFF, direction = Direction.BIDIRECTIONAL, name = "Disconnect")
public class PacketDisconnect extends Packet {
	@ProtocolField(name = "Reason")
	public String reason;
	
	public PacketDisconnect(){
		this("");
	}
	
	public PacketDisconnect(String reason){
		this.reason = reason;
	}
}
