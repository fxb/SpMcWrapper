package de.felixbruns.minecraft.handlers.commands;

import java.util.Map.Entry;

import de.felixbruns.minecraft.SpMcGroup;
import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.SpMcStorage;
import de.felixbruns.minecraft.handlers.CommandHandler;
import de.felixbruns.minecraft.handlers.commands.annotations.CommandProvider;
import de.felixbruns.minecraft.protocol.Colors;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketAddToInventory;
import de.felixbruns.minecraft.protocol.packets.PacketDisconnect;
import de.felixbruns.minecraft.util.ItemOrBlock;

@CommandProvider(commands = {"setgroup", "give", "quit", "restart"})
public class AdminCommandHandler extends CommandHandler implements Colors {
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	/*
    	 * The 'quit' command stops and waits for the minecraft
    	 * server to shut down, then it also terminates the wrapper.
    	 */
    	if(command.equals("setgroup")){
    		if(args.length != 2){
    			player.sendUsage("setgroup <player> <group>");
    			
    			return null;
    		}
    		
    		SpMcPlayer p = player.getWrapper().getPlayers().get(args[0]);
    		SpMcGroup  g = player.getWrapper().getGroups().get(args[1]);
    		
    		if(p == null || g != null){
    			if(p.getGroup() != null){
    				p.getGroup().removePlayer(args[0]);
    			}
    			
    			g.addPlayer(args[0]);
    			p.setGroup(g);
    			
    			player.sendMessage(COLOR_LIGHT_GREEN, "%s's group is now '%s'!", args[0], args[1]);
    		}
    		else{
    			player.sendMessage(COLOR_LIGHT_RED, "That player or group doesn't exist!");
    		}
    		
    		SpMcStorage.saveGroups(player.getWrapper().getGroups());
    		
    		return null;
    	}
    	/*
    	 * The 'give' command gives a specified amout of items to a player.
    	 */
    	if(command.equals("give")){
    		if(args.length < 2 || args.length > 3){
    			player.sendUsage("give [player] <item> <amount>");
    			
    			return null;
    		}
    		
    		SpMcPlayer  p     = null;
    		ItemOrBlock item  = null;
    		int         count = 0;
    		
    		if(args.length == 2){
    			p = player;
    			
    			try {
    				item = ItemOrBlock.getById(Integer.parseInt(args[0]));
    			}
    			catch(NumberFormatException e){
    				item = ItemOrBlock.getByName(args[0]);
    			}
    			
    			count = Integer.parseInt(args[1]);
    		}
    		else if(args.length == 3){
    			p = player.getWrapper().getPlayers().get(args[0]);
    			
    			try {
    				item = ItemOrBlock.getById(Integer.parseInt(args[1]));
    			}
    			catch(NumberFormatException e){
    				item = ItemOrBlock.getByName(args[1]);
    			}
    			
    			count = Integer.parseInt(args[2]);
    		}
    		
    		if(p == null){
    			player.sendMessage(COLOR_LIGHT_RED, "That player doesn't exist!");
    			
    			return null;
    		}
    		
    		if(item == null || item.equals(ItemOrBlock.BLOCK_AIR)){
    			player.sendMessage(COLOR_LIGHT_RED, "Unknown item!");
    			
    			return null;
    		}
    		
    		PacketAddToInventory a = new PacketAddToInventory();
    		
			a.item = item.getId();
    		a.life = 0;
    		
    		int n = count;
    		
			while(n > 0){
				a.count = (byte)Math.min(n, 64);
				
	    		p.sendToClient(a);
	    		
	    		n -= a.count;
			}
    		
    		if(p == player){
    			player.sendMessage(COLOR_LIGHT_GREEN, "You gave yourself %d '%s'!", count, item);
    		}
    		else{
    			p.sendMessage(COLOR_LIGHT_GREEN, "You received %d '%s' from %s!", count, item, player.getName());
    			player.sendMessage(COLOR_LIGHT_GREEN, "Gave %s %d '%s'!", p.getName(), count, item);
    		}
    		
    		return null;
    	}
    	/*
    	 * The 'quit' command stops and waits for the minecraft
    	 * server to shut down, then it also terminates the wrapper.
    	 */
    	else if(command.equals("quit")){
    	   	if(!player.getWrapper().isConsoleAvailable()){
        		player.sendMessage(COLOR_LIGHT_RED + "Sorry, the server console is not available!");
    	   		
    	   		return null;
        	}
    		
    		for(Entry<String, SpMcPlayer> entry : player.getWrapper().getPlayers().entrySet()){
    			SpMcPlayer p = entry.getValue();
    			
    			p.sendToClient(new PacketDisconnect("Server is stopping..."));
    			p.disconnect();
    		}
    	   	
    		player.getWrapper().stopServer();
    		
    		System.exit(0);
    		
    		return null;
    	}
    	/* The 'restart' command restarts the minecraft server. */
    	else if(command.equals("restart")){
    	   	if(!player.getWrapper().isConsoleAvailable()){
        		player.sendMessage(COLOR_LIGHT_RED + "Sorry, the server console is not available!");
    	   		
    	   		return null;
        	}
    		
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
