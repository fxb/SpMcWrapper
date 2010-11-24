package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x21, type = "Server", name = "EntityRelativeMoveAndLook")
public class PacketEntityRelativeMoveAndLook extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "X")
	public byte x;
	
	@ProtocolField(name = "Y")
	public byte y;
	
	@ProtocolField(name = "Z")
	public byte z;
	
	@ProtocolField(name = "Yaw")
	public byte yaw;
	
	@ProtocolField(name = "Pitch")
	public byte pitch;
}
