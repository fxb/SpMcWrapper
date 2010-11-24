package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x15, direction = Direction.CLIENT_SERVER, name = "PickupSpawn")
public class PacketPickupSpawn extends Packet {	
	@ProtocolField(name = "Entity ID")
	public int eid;
	
	@ProtocolField(name = "Item")
	public short item;
	
	@ProtocolField(name = "Count")
	public byte count;
	
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
	
	@ProtocolField(name = "Roll")
	public byte roll;
}
