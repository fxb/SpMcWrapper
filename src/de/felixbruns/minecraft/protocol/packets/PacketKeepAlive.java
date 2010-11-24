package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x00, direction = Direction.BIDIRECTIONAL, name = "KeepAlive")
public class PacketKeepAlive extends Packet {
	
}
