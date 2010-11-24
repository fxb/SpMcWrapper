package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x16, direction = Direction.SERVER_CLIENT, name = "CollectItem")
public class PacketCollectItem extends Packet {
	@ProtocolField(name = "Collected Item")
	public int collected;
	
	@ProtocolField(name = "Collector")
	public int collector;
}
