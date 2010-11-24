package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x26, direction = Direction.SERVER_CLIENT, name = "EntityDie")
public class PacketEntityDie extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "Unknown")
	public byte unknown;
}
