package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x20, direction = Direction.SERVER_CLIENT, name = "EntityLook")
public class PacketEntityLook extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "Yaw")
	public byte yaw;
	
	@ProtocolField(name = "Pitch")
	public byte pitch;
}
