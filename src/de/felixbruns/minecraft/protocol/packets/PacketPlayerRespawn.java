package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x09, direction = Direction.BIDIRECTIONAL, name = "PlayerRespawn")
public class PacketPlayerRespawn extends Packet {

}
