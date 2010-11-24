package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x07, type = "Client", name = "UseEntity")
public class PacketUseEntity extends Packet {
	@ProtocolField(name = "User")
	public int user;
	
	@ProtocolField(name = "Target")
	public int target;
	
	@ProtocolField(name = "Type")
	public byte type; /* 0x00: Chest, Furnace, Minecart, Boat, ... 0x01: Cow, Sheep, Pig, Player?, ... */
}
