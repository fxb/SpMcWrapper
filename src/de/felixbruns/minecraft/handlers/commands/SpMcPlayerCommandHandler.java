package de.felixbruns.minecraft.handlers.commands;

import java.util.Map.Entry;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.handlers.commands.annotations.CommandHandler;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.packets.Packet;

@CommandHandler(commands = {"players", "clear"})
public class SpMcPlayerCommandHandler extends SpMcBaseCommandHandler implements ChatColors {
    /**
     * Handle serveral generic commands.
     * 
     * @param player   The associated player.
     * @param command  The command that was sent.
     * @param args     The arguments to the command.
     */
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	if(command.equals("players")){
			player.sendMessage("");
    		player.sendMessage(COLOR_LIGHT_YELLOW + "Online players:");
    		
    		for(Entry<String, SpMcPlayer> entry : player.getWrapper().getPlayers().entrySet()){
    			SpMcPlayer p = entry.getValue();
    			
    			player.sendMessage(String.format(
        			" %s%s (%.2fm)", p.getDisplayName(), COLOR_LIGHT_YELLOW, p.getPosition().distance(player.getPosition())
        		));
    		}
    		
			player.sendMessage("");
    		
    		return null;
    	}
    	else if(command.equals("clear")){
    		for(int i = 0; i < 20; i++){
    			player.sendMessage("");
    		}
    		
    		return null;
    	}
    	
    	return packet;
    }
}
