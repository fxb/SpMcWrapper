package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x1D, direction = Direction.SERVER_CLIENT, name = "DestroyEntity")
public class PacketDestroyEntity extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
}
