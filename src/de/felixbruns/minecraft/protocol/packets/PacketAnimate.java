package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x12, direction = Direction.CLIENT_SERVER, name = "Animate")
public class PacketAnimate extends Packet {	
	@ProtocolField(name = "Player ID")
	public int eid;
	
	@ProtocolField(name = "What")
	public byte what; /* 0x01: Arm, 0x02: ?, 0x68: Crouch, 0x69: Stand up. */
}
