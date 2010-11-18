package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcClient;
import de.felixbruns.minecraft.protocol.packets.Packet;

public class SpMcCommandHandler implements SpMcHandler {
    public Packet handleClientPacket(SpMcClient client, Packet packet){
    	System.out.println(packet);
    	
	    return packet;
    }
    
    public Packet handleServerPacket(SpMcClient client, Packet packet){
    	System.out.println(packet);
    	
	    return packet;
    }
}
