package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x07, type = "Client", name = "Use Entity")
public class PacketUseEntity extends Packet {
	@ProtocolField(name = "User")
	public int user;
	
	@ProtocolField(name = "Target")
	public int target;
}
