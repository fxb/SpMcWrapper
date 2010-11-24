package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x08, direction = Direction.SERVER_CLIENT, name = "PlayerHealth")
public class PacketPlayerHealth extends Packet {
	@ProtocolField(name = "Health")
	public byte health;
}
