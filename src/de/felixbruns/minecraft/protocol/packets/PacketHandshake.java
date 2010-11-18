package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x02, type = "Client & Server", name = "Handshake")
public class PacketHandshake extends Packet {
	@ProtocolField(name = "Username or Connection Hash")
	public String usernameOrConnectionHash;
}
