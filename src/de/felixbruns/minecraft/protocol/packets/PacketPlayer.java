package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x0A, type = "Client", name = "Player")
public class PacketPlayer extends Packet {
	@ProtocolField(name = "On Ground")
	public boolean onGround;
}
