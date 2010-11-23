package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.packets.Packet;

public interface SpMcHandler {
	public Packet handleClientPacket(SpMcPlayer player, Packet packet);
	public Packet handleServerPacket(SpMcPlayer player, Packet packet);
}
