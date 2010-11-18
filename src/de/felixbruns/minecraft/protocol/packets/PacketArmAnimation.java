package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x12, type = "Client", name = "Arm Animation")
public class PacketArmAnimation extends Packet {	
	@ProtocolField(name = "Player ID")
	public int eid;
	
	@ProtocolField(name = "Animate")
	public boolean animate;
}
