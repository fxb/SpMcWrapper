package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x32, direction = Direction.SERVER_CLIENT, name = "PreChunk")
public class PacketPreChunk extends Packet {
	@ProtocolField(name = "Chunk X")
	public int x;
	
	@ProtocolField(name = "Chunk Z")
	public int z;
	
	@ProtocolField(name = "Mode")
	public boolean mode;
}
