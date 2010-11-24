package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x08, type = "Server", name = "PlayerHealth")
public class PacketPlayerHealth extends Packet {
	@ProtocolField(name = "Health")
	public byte health;
}
