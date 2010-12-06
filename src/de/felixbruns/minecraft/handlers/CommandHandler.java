package de.felixbruns.minecraft.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.Colors;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;

/**
 * Handles client packets and checks if they are chat messages containing
 * a command. If yes, this will parse the command into parts (command and
 * arguments) and call a handler to handle the command.
 * 
 * @author Felix Bruns <felixbruns@web.de>
 *
 */
public abstract class CommandHandler extends PacketAdapter implements Colors {
	/**
	 * Handle a client packet, check if it is a chat message and if it starts
	 * with the command character. If it does, parse and handle it as a command.
	 * Otherwise just forward the packet.
	 * 
	 * @param player The associated player.
	 * @param packet The packet that was sent.
	 */
    public Packet handleClientPacket(SpMcPlayer player, Packet packet){
    	String commandChar = player.getWrapper().getSettings().getCommandChar();
    	
    	if(packet instanceof PacketChatMessage){
    		PacketChatMessage chat = (PacketChatMessage)packet;
    		
    		if(chat.message.startsWith(commandChar)){
    			String[] parts = this.parseCommand(chat.message.substring(1));
    			
    			if(parts.length > 0){
        			String   command = parts[0];
        			String[] args    = Arrays.copyOfRange(parts, 1, parts.length);
        			
        			if(!CommandFinder.isCommandAvailable(command)){
        				player.sendMessage(COLOR_LIGHT_YELLOW + "That command is not available!");
        				
        				System.out.println(player.getName() + " tried to issue the unavailable command '" + command + "'.");
        				
        				return null;
        			}
        			else if(!player.getGroup().isCommandAllowed(command)){
        				player.sendMessage(COLOR_LIGHT_RED + "You're not allowed to do that!");
        				
        				System.out.println(player.getName() + " tried to issue the restricted command '" + command + "'!");
        				
        				return null;
        			}
        			else{
        				System.out.println(player.getName() + " issued command '" + command + "'.");
        				
        				return this.handleCommand(player, packet, command, args);
        			}
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
     * Split a chat message into an array of strings.<br><br>
     * 
     * <b>Note:</b> Supports quoted strings.
     * 
     * @param message The chat message to split.
     * 
     * @return An array of strings.
     */
    private String[] parseCommand(String message){
		List<String> tokens = new ArrayList<String>();
		StringBuffer token  = new StringBuffer();
		boolean      quoted = false;
		
		/* Loop over all characters. */
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			
			/* 
			 * If character is a space, either append it
			 * to the current token if it was quoted, or
			 * end the current token and add it to the list
			 * of tokens.
			 */
			if(c == ' '){
				if(quoted){
					token.append(c);
				}
				else if(token.length() > 0){
					tokens.add(token.toString());
					
					token.setLength(0);
				}
			}
			/* If character is a quote, toggle quoted flag. */
			else if(c == '"'){
				quoted = !quoted;
			}
			/* Append any other character. */
			else{
				token.append(c);
			}
		}
		
		/* Add the last token to the list of tokens. */
		if(token.length() > 0){
			tokens.add(token.toString());
		}
		
		return (String[])tokens.toArray(new String[0]);
    }
}
