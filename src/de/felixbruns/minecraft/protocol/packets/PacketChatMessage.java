package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x03, type = "Client & Server", name = "Chat Message")
public class PacketChatMessage extends Packet {
	@ProtocolField(name = "Message")
	public String message;
}