package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x12, type = "Client", name = "Arm Animation")
public class PacketAnimate extends Packet {	
	@ProtocolField(name = "Player ID")
	public int eid;
	
	@ProtocolField(name = "What")
	public byte what; /* 0x01: Arm, 0x02: ? */
}
