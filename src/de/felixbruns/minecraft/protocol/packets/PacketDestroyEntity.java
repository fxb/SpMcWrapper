package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x1D, type = "Server", name = "DestroyEntity")
public class PacketDestroyEntity extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
}
