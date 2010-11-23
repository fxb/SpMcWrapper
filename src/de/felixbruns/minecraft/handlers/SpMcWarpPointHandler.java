package de.felixbruns.minecraft.handlers;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPositionAndLook;

public class SpMcWarpPointHandler extends SpMcCommandHandler implements ChatColors {
    /**
     * Handle a warp command sent by a player.
     * 
     * @param player   The associated player.
     * @param command  The command that was sent.
     * @param args     The arguments to the command.
     */
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	if(command.equals("add-warp")){
    		if(args.length != 1){
    			player.sendMessage(COLOR_LIGHT_RED + "Usage: !add-warp <name>");
    			
    			return null;
    		}
    		
    		player.getWarpPoints().put(args[0], player.getPosition());
    		
    		return null;
    	}
    	if(command.equals("add-global-warp")){
    		if(args.length != 1){
    			player.sendMessage(COLOR_LIGHT_RED + "Usage: !add-global-warp <name>");
    			
    			return null;
    		}
    		
    		player.getWrapper().getWarpPoints().put(args[0], player.getPosition());
    		
    		return null;
    	}
    	else if(command.equals("delete-warp")){
    		if(args.length != 1){
    			player.sendMessage(COLOR_LIGHT_RED + "Usage: !delete-warp <name>");
    			
    			return null;
    		}
    		
    		player.getWarpPoints().remove(args[0]);
    		
    		return null;
    	}
    	else if(command.equals("delete-global-warp")){
    		if(args.length != 1){
    			player.sendMessage(COLOR_LIGHT_RED + "Usage: !delete-global-warp <name>");
    			
    			return null;
    		}
    		
    		player.getWrapper().getWarpPoints().remove(args[0]);
    		
    		return null;
    	}
    	else if(command.equals("list-warp")){
    		int n;
    		
			player.sendMessage(COLOR_LIGHT_BLUE + "Global warp points:");
			
			if(player.getWrapper().getWarpPoints().isEmpty()){
				player.sendMessage(COLOR_LIGHT_BLUE + "  None");
			}
			
			n = 1;
			
    		for(String name : player.getWrapper().getWarpPoints().keySet()){
    			player.sendMessage(COLOR_LIGHT_BLUE + String.format(
    				" %2d. %s", n++, name)
    			);
    		}
    		
			player.sendMessage(COLOR_LIGHT_BLUE + "Personal warp points:");
			
			if(player.getWarpPoints().isEmpty()){
				player.sendMessage(COLOR_LIGHT_BLUE + "  None");
			}
			
			n = 1;
			
    		for(String name : player.getWarpPoints().keySet()){
    			player.sendMessage(COLOR_LIGHT_BLUE + String.format(
    				" %2d. %s %f", n++, name, player.getWarpPoints().get(name).distance(player.getPosition()))
    			);
    		}
    		
    		return null;
    	}
    	else if(command.equals("warp")){
    		if(args.length != 1){
    			player.sendMessage(COLOR_LIGHT_RED + "Usage: !warp <name>");
    			
    			return null;
    		}
    		
    		Position target = player.getWarpPoints().get(args[0]);
    		
    		if(target == null){
    			target = player.getWrapper().getWarpPoints().get(args[0]);
    		}
    		
    		if(target != null){
    			this.warp(player, target);
    		}
    		
    		return null;
    	}
    	else if(command.equals("warp-to")){
    		if(args.length != 1){
    			player.sendMessage(COLOR_LIGHT_RED + "Usage: !warp <name>");
    			
    			return null;
    		}
    		
    		SpMcPlayer target = player.getWrapper().getPlayers().get(args[0]);
    		
    		if(target != null){
    			this.warp(player, target);
    		}
    		
    		return null;
    	}
    	
    	return packet;
    }
    
	/**
	 * Warp a player to another player.
	 * 
	 * @param player The player to warp.
	 * @param target The target player to warp to.
	 */
	private void warp(SpMcPlayer player, SpMcPlayer target){
		warp(player, target.getPosition());
	}
	
	/**
	 * Warp a player to a target position.
	 * 
	 * @param player The player to warp.
	 * @param target The target position.
	 */
	private void warp(SpMcPlayer player, Position target){
		PacketPlayerPositionAndLook packet = new PacketPlayerPositionAndLook();
		
		/*PacketAddToInventory add = new PacketAddToInventory();
		
		add.item  = 7;
		add.count = 64;
		add.life  = 0;
		
		player.sendToClient(add);*/
		
		/* Fill the packet with position information. */
		packet.x        = player.getPosition().x;
		packet.z        = player.getPosition().z;
		packet.yaw      = 0.0f;
		packet.pitch    = 0.0f;
		packet.onGround = false;
		
		/* Fool the server by sending a packet with a Y axis value of 128. */
		packet.y        = 128.0;
		packet.stance   = 128.0 + 1.62;
		
		player.sendToServer(packet);
		
		/* Fool the server by sending a packet with a Y axis value of 128. */
		packet.x        = target.x;
		packet.z        = target.z;
		
		player.sendToServer(packet);
		
		/* Now send the actual right Y axis value to both the server and the client. */
		packet.y        = target.y;
		packet.stance   = target.y + 1.62;
		
		player.sendToServer(packet);
		
		/* We need to swap Y and stance here... (see protocol specification). */
		packet.y        = target.y + 1.62;
		packet.stance   = target.y;
		
		player.sendToClient(packet);
	}
}
