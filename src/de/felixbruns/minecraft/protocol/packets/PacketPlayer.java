package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x0A, direction = Direction.CLIENT_SERVER, name = "Player")
public class PacketPlayer extends Packet {
	@ProtocolField(name = "On Ground")
	public boolean onGround;
}
