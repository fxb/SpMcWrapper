package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x04, type = "Server", name = "TimeUpdate")
public class PacketTimeUpdate extends Packet {
	@ProtocolField(name = "Time")
	public long time;
}
