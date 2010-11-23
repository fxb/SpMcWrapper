package de.felixbruns.minecraft.handlers;

import java.util.Arrays;
import java.util.StringTokenizer;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;

public abstract class SpMcCommandHandler extends SpMcAdapter {
	/**
	 * Handle a client packet, check if it is a chat message and if it starts
	 * with an exclamation mark. If it does, parse and handle it as a command.
	 * Otherwise just forward the packet.
	 * 
	 * @param player The associated player.
	 * @param packet The packet that was sent.
	 */
    public Packet handleClientPacket(SpMcPlayer player, Packet packet){
    	if(packet instanceof PacketChatMessage){
    		PacketChatMessage chat = (PacketChatMessage)packet;
    		
    		if(chat.message.startsWith("!")){
    			String[] parts = this.parseCommand(chat.message);
    			
    			if(parts.length > 0){
        			String   command = parts[0];
        			String[] args    = Arrays.copyOfRange(parts, 1, parts.length);
        			
        			return this.handleCommand(player, packet, command, args);
    			}
    		}
    	}
    	
	    return packet;
    }
    
    /**
     * Handle a command sent by a player.
     * 
     * @param player  The associated player.
     * @param command The command that was sent.
     * @param args    The arguments to the command.
     */
    public abstract Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args);
    
    /**
     * Split a chat message into an array of strings.
     * 
     * @param message The chat message to split.
     * 
     * @return An array of strings.
     */
    private String[] parseCommand(String message){
    	StringTokenizer tokenizer = new StringTokenizer(message, "! ");
		String          parts[]   = new String[tokenizer.countTokens()];
		int             i         = 0;
		
		while(tokenizer.hasMoreTokens()){
			parts[i++] = tokenizer.nextToken();
		}
		
		return parts;
    }
}
