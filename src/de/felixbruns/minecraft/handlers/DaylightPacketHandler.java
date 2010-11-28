package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketTimeUpdate;

/**
 * Modifies the 'TimeUpdate' packet sent from the server to always have
 * a value of 3600. This way, it's always noon for the client.<br><br>
 * 
 * <b>Note:</b> Monsters still spawn at night on the server side though!
 * 
 * @author Felix Bruns <felixbruns@web.de>
 *
 */
public class DaylightPacketHandler extends PacketAdapter {
	public Packet handleServerPacket(SpMcPlayer player, Packet packet){
		if(packet instanceof PacketTimeUpdate){
			((PacketTimeUpdate)packet).time = 3600;
		}
		
		return packet;
	}
}
