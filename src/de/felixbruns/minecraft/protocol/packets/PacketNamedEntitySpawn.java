package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x14, direction = Direction.SERVER_CLIENT, name = "NamedEntitySpawn")
public class PacketNamedEntitySpawn extends Packet {	
	@ProtocolField(name = "Entity ID")
	public int eid;
	
	@ProtocolField(name = "Name")
	public String name;
	
	@ProtocolField(name = "X")
	public int x;
	
	@ProtocolField(name = "Y")
	public int y;
	
	@ProtocolField(name = "Z")
	public int z;
	
	@ProtocolField(name = "Rotation")
	public byte rotation;
	
	@ProtocolField(name = "Pitch")
	public byte pitch;
	
	@ProtocolField(name = "Current Item")
	public short item;
}
