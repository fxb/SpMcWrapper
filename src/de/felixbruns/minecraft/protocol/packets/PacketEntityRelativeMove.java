package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x1F, type = "Server", name = "EntityRelativeMove")
public class PacketEntityRelativeMove extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "X")
	public byte x;
	
	@ProtocolField(name = "Y")
	public byte y;
	
	@ProtocolField(name = "Z")
	public byte z;
}
