package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcClient;
import de.felixbruns.minecraft.protocol.packets.Packet;

public interface SpMcHandler {
	public Packet handleClientPacket(SpMcClient client, Packet packet);
	public Packet handleServerPacket(SpMcClient client, Packet packet);
}
