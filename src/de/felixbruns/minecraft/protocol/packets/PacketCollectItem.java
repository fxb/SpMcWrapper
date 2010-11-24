package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x16, type = "Server", name = "CollectItem")
public class PacketCollectItem extends Packet {
	@ProtocolField(name = "Collected Item")
	public int collected;
	
	@ProtocolField(name = "Collector")
	public int collector;
}
