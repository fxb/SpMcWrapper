package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x1C, direction = Direction.SERVER_CLIENT, name = "EntityVelocity")
public class PacketEntityVelocity extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "Velocity X")
	public short velocityX;
	
	@ProtocolField(name = "Velocity Y")
	public short velocityY;
	
	@ProtocolField(name = "Velocity Z")
	public short velocityZ;
}
