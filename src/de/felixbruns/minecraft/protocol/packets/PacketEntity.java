package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x1E, type = "Server", name = "Entity")
public class PacketEntity extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
}
