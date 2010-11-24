package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x00, type = "Client & Server", name = "KeepAlive")
public class PacketKeepAlive extends Packet {
	
}
