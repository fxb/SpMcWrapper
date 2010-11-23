package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = (byte)0xFF, type = "Client & Server", name = "Disconnect")
public class PacketDisconnect extends Packet {
	@ProtocolField(name = "Reason")
	public String reason;
}
