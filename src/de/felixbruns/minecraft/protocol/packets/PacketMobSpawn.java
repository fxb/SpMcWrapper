package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x18, direction = Direction.SERVER_CLIENT, name = "MobSpawn")
public class PacketMobSpawn extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "Type")
	public byte type;
	
	@ProtocolField(name = "X")
	public int x;
	
	@ProtocolField(name = "Y")
	public int y;
	
	@ProtocolField(name = "Z")
	public int z;
	
	@ProtocolField(name = "Yaw")
	public byte yaw;
	
	@ProtocolField(name = "Pitch")
	public byte pitch;
}
