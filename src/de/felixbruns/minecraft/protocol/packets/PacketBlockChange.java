package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x35, direction = Direction.SERVER_CLIENT, name = "BlockChange")
public class PacketBlockChange extends Packet {
	@ProtocolField(name = "Chunk X")
	public int x;
	
	@ProtocolField(name = "Chunk Y")
	public byte y;
	
	@ProtocolField(name = "Chunk Z")
	public int z;
	
	@ProtocolField(name = "Type")
	public byte type;
	
	@ProtocolField(name = "Metadata")
	public byte metadata;
}
