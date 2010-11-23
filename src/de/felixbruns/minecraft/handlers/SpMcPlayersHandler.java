package de.felixbruns.minecraft.handlers;

import java.util.Map.Entry;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.packets.Packet;

public class SpMcPlayersHandler extends SpMcCommandHandler implements ChatColors {
    /**
     * Handle a players command sent by a player.
     * 
     * @param player   The associated player.
     * @param command  The command that was sent.
     * @param args     The arguments to the command.
     */
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	if(command.equals("players")){
    		player.sendMessage(COLOR_LIGHT_YELLOW + "Online players:");
    		
    		for(Entry<String, SpMcPlayer> entry : player.getWrapper().getPlayers().entrySet()){
    			String     name = entry.getKey();
    			SpMcPlayer p    = entry.getValue();
    			
    			player.sendMessage(COLOR_LIGHT_YELLOW + String.format(
        			" %s (%.2fm)", name, p.getPosition().distance(player.getPosition())
        		));
    		}
    		
    		return null;
    	}
    	
    	return packet;
    }
}
