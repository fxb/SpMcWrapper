package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x1C, type = "Server", name = "EntityVelocity")
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
