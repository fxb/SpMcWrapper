package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.packets.Packet;

/**
 * Implements the {@link PacketHandler} interface, so subclasses can
 * easily override just one of the handlers (e.g. just the server packet
 * handler or just the client packet handler), without needing to care
 * about the other handler.
 * 
 * @author Felix Bruns <felixbruns@web.de>
 *
 */
public class PacketAdapter implements PacketHandler {
	public Packet handleClientPacket(SpMcPlayer player, Packet packet){
		return packet;
	}
	
	public Packet handleServerPacket(SpMcPlayer player, Packet packet){
		return packet;
	}
}
