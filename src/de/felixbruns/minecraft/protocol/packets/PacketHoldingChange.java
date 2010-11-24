package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x10, direction = Direction.CLIENT_SERVER, name = "HoldingChange")
public class PacketHoldingChange extends Packet {	
	@ProtocolField(name = "Player Entity ID")
	public int eid;
	
	@ProtocolField(name = "Block or Item ID")
	public short id;
}
