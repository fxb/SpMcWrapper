package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x04, direction = Direction.SERVER_CLIENT, name = "TimeUpdate")
public class PacketTimeUpdate extends Packet {
	@ProtocolField(name = "Time")
	public long time;
}
