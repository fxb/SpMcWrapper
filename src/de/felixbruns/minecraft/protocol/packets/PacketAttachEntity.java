package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x27, direction = Direction.SERVER_CLIENT, name = "AttachEntity")
public class PacketAttachEntity extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "Vehicle Entity Id")
	public int vid;
}
