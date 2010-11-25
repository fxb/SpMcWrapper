package de.felixbruns.minecraft.handlers.commands;

import java.util.Map.Entry;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.handlers.commands.annotations.CommandHandler;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketDisconnect;

@CommandHandler(commands = {"quit", "restart"})
public class SpMcAdminCommandHandler extends SpMcBaseCommandHandler implements ChatColors {
    /**
     * Handle admin commands.
     * 
     * @param player   The associated player.
     * @param command  The command that was sent.
     * @param args     The arguments to the command.
     */
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	if(command.equals("quit")){
    		player.getWrapper().stopServer();
    		
    		System.exit(0);
    		
    		return null;
    	}
    	else if(command.equals("restart")){
    		for(Entry<String, SpMcPlayer> entry : player.getWrapper().getPlayers().entrySet()){
    			SpMcPlayer p = entry.getValue();
    			
    			p.sendToClient(new PacketDisconnect("Server is restarting..."));
    			p.disconnect();
    		}
    		
    		player.getWrapper().restartServer();
    		
    		return null;
    	}
    	
    	return packet;
    }
}
