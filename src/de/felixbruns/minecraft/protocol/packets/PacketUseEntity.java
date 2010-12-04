package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x07, direction = Direction.CLIENT_SERVER, name = "UseEntity")
public class PacketUseEntity extends Packet {
	@ProtocolField(name = "User")
	public int user;
	
	@ProtocolField(name = "Target")
	public int target;
	
	@ProtocolField(name = "Type")
	public byte type;
}
