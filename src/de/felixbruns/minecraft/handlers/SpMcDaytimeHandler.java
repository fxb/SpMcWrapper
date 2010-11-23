package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketTimeUpdate;

public class SpMcDaytimeHandler extends SpMcAdapter {
	public Packet handleServerPacket(SpMcPlayer player, Packet packet){
		if(packet instanceof PacketTimeUpdate){
			((PacketTimeUpdate)packet).time = 0;
		}
		
		return packet;
	}
}
