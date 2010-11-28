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
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	/* 
    	 * The 'help' command sends a list of available commands
    	 * to the player issuing it.
    	 */
    	if(command.equals("help")){
			player.sendMessage("");
    		player.sendMessage(COLOR_LIGHT_YELLOW, "Available commands:");
    		
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
    	/* 
    	 * The 'players' command sends a list of online players
    	 * to the player issuing it.
    	 */
    	else if(command.equals("players")){
			player.sendMessage("");
    		player.sendMessage(COLOR_LIGHT_YELLOW, "Online players:");
    		
    		for(Entry<String, SpMcPlayer> entry : player.getWrapper().getPlayers().entrySet()){
    			SpMcPlayer p = entry.getValue();
    			
    			player.sendMessage(
    				COLOR_WHITE, "  %s%s (%.2fm)",
    				p.getDisplayName(), COLOR_LIGHT_YELLOW,
    				p.getPosition().distanceTo(player)
        		);
    		}
    		
			player.sendMessage("");
    		
    		return null;
    	}
    	/* 
    	 * The 'clear' command sends 20 empty messages to the
    	 * player issuing it, effectively clearing their chat log.
    	 */
    	else if(command.equals("clear")){
    		for(int i = 0; i < 20; i++){
    			player.sendMessage("");
    		}
    		
    		return null;
    	}
    	
    	return packet;
    }
}
