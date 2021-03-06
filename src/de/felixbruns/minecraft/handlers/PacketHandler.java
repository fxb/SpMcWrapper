package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.packets.Packet;

/**
 * Defines methods for handling client and server packets.
 * 
 * @author Felix Bruns <felixbruns@web.de>
 *
 */
public interface PacketHandler {
	public Packet handleClientPacket(SpMcPlayer player, Packet packet);
	public Packet handleServerPacket(SpMcPlayer player, Packet packet);
}
