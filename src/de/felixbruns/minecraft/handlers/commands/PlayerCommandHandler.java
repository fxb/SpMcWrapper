package de.felixbruns.minecraft.handlers.commands;

import java.util.Map.Entry;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.handlers.CommandFinder;
import de.felixbruns.minecraft.handlers.CommandHandler;
import de.felixbruns.minecraft.handlers.commands.annotations.CommandProvider;
import de.felixbruns.minecraft.protocol.Colors;
import de.felixbruns.minecraft.protocol.packets.Packet;

@CommandProvider(commands = {"help", "players", "clear"})
public class PlayerCommandHandler extends CommandHandler implements Colors {
    /**
     * Handle serveral generic commands.
     * 
     * @param player   The associated player.
     * @param command  The command that was sent.
     * @param args     The arguments to the command.
     */
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	if(command.equals("help")){
			player.sendMessage("");
    		player.sendMessage(COLOR_LIGHT_YELLOW + "Available commands:");
    		
    		StringBuilder msg = new StringBuilder(" ");
    		
    		for(String cmd : CommandFinder.getCommands()){
    			if(player.getGroup().isCommandAllowed(cmd)){
    				msg.append(cmd);
    				msg.append(", ");
    			}
    		}
    		
    		player.sendMessage(msg.toString());
			player.sendMessage("");
    		
    		return null;
    	}
    	else if(command.equals("players")){
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
