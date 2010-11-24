package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x0C, direction = Direction.CLIENT_SERVER, name = "PlayerLook")
public class PacketPlayerLook extends Packet {
	@ProtocolField(name = "Yaw")
	public float yaw;
	
	@ProtocolField(name = "Pitch")
	public float pitch;
	
	@ProtocolField(name = "On Ground")
	public boolean onGround;
}
