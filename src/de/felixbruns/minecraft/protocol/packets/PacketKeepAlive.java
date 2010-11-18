package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x00, type = "Client & Server", name = "Keep Alive")
public class PacketKeepAlive extends Packet {
	
}
