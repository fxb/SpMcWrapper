package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.packets.Packet;

public class SpMcPacketAdapter implements SpMcPacketHandler {
	public Packet handleClientPacket(SpMcPlayer player, Packet packet){
		return packet;
	}
	
	public Packet handleServerPacket(SpMcPlayer player, Packet packet){
		return packet;
	}
}
