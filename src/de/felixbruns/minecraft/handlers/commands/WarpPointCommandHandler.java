package de.felixbruns.minecraft.handlers.commands;

import java.util.Map.Entry;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.SpMcStorage;
import de.felixbruns.minecraft.handlers.CommandHandler;
import de.felixbruns.minecraft.handlers.commands.annotations.CommandProvider;
import de.felixbruns.minecraft.protocol.Colors;
import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPositionAndLook;

@CommandProvider(commands = {"setwarp", "setglobalwarp", "deletewarp", "deleteglobalwarp", "listwarps", "warp", "warpto", "warptocoords"})
public class WarpPointCommandHandler extends CommandHandler implements Colors {
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	/* Set a personal warp point. */
    	if(command.equals("setwarp")){
    		if(args.length != 1){
    			player.sendUsage("addwarp <name>");
    			
    			return null;
    		}
    		
    		player.getWarpPoints().put(args[0], player.getPosition());
			player.sendMessage(COLOR_LIGHT_GREEN, "Personal warp point '%s' has been set!", args[0]);
    		
    		SpMcStorage.saveWarpPoints(player.getName(), player.getWarpPoints());
    		
    		return null;
    	}
    	/* Set a global warp point. */
    	else if(command.equals("setglobalwarp")){
    		if(args.length != 1){
    			player.sendUsage("addglobalwarp <name>");
    			
    			return null;
    		}
    		
    		player.getWrapper().getWarpPoints().put(args[0], player.getPosition());
			player.sendMessage(COLOR_LIGHT_GREEN, "Global warp point '%s' has been set!", args[0]);
    		
    		SpMcStorage.saveWarpPoints(player.getWrapper().getWarpPoints());
    		
    		return null;
    	}
    	/* Delete a personal warp point. */
    	else if(command.equals("deletewarp")){
    		if(args.length != 1){
    			player.sendUsage("deletewarp <name>");
    			
    			return null;
    		}
    		
    		if(player.getWarpPoints().remove(args[0]) != null){
    			player.sendMessage(COLOR_LIGHT_GREEN, "Warp point '%s' has been deleted!", args[0]);
    		}
    		else{
    			player.sendMessage(COLOR_LIGHT_RED, "That warp point didn't exist!");
    		}
    		
    		SpMcStorage.saveWarpPoints(player.getName(), player.getWarpPoints());
    		
    		return null;
    	}
    	/* Delete a personal global warp point. */
    	else if(command.equals("deleteglobalwarp")){
    		if(args.length != 1){
    			player.sendUsage("deleteglobalwarp <name>");
    			
    			return null;
    		}
    		
    		if(player.getWrapper().getWarpPoints().remove(args[0]) != null){
    			player.sendMessage(COLOR_LIGHT_GREEN, "Global warp point '%s' has been deleted!", args[0]);
    		}
    		else{
    			player.sendMessage(COLOR_LIGHT_RED, "That warp point didn't exist!");
    		}
    		
    		SpMcStorage.saveWarpPoints(player.getWrapper().getWarpPoints());
    		
    		return null;
    	}
    	/* List all personal and global warp points and their distance. */
    	else if(command.equals("listwarps")){
    		int n;

			player.sendMessage("");
			player.sendMessage(COLOR_LIGHT_GREEN, "Global warp points:");
			
			if(player.getWrapper().getWarpPoints().isEmpty()){
				player.sendMessage(COLOR_LIGHT_GREEN, "  None");
			}
			
			n = 1;
			
    		for(Entry<String, Position> entry : player.getWrapper().getWarpPoints().entrySet()){
    			String   name     = entry.getKey();
    			Position position = entry.getValue();
    			
    			player.sendMessage(
    				COLOR_LIGHT_GREEN, " %2d. %s (%.2fm)", n++,
    				name, position.distanceTo(player)
    			);
    		}
    		
			player.sendMessage("");
			player.sendMessage(COLOR_LIGHT_GREEN, "Personal warp points:");
			
			if(player.getWarpPoints().isEmpty()){
				player.sendMessage(COLOR_LIGHT_GREEN, "  None");
			}
			
			n = 1;
			
    		for(Entry<String, Position> entry : player.getWarpPoints().entrySet()){
    			String   name     = entry.getKey();
    			Position position = entry.getValue();
    			
    			player.sendMessage(
    				COLOR_LIGHT_GREEN, " %2d. %s (%.2fm)",
    				n++, name, position.distanceTo(player)
    			);
    		}
    		
			player.sendMessage("");
    		
    		return null;
    	}
    	/* 
    	 * Warp to a given warp point. This will first lookit up in
    	 * the personal warp points, then in the global warp points.
    	 */
    	else if(command.equals("warp")){
    		if(args.length != 1){
    			player.sendUsage("warp <name>");
    			
    			return null;
    		}
    		
    		Position target = player.getWarpPoints().get(args[0]);
    		
    		if(target == null){
    			target = player.getWrapper().getWarpPoints().get(args[0]);
    		}
    		
    		if(target != null){
    			this.warp(player, target);
    			
    			player.sendMessage(COLOR_LIGHT_GREEN, "Warped to '%s'!", args[0]);
    		}
    		else{
    			player.sendMessage(COLOR_LIGHT_RED, "That warp point doesn't exist!");
    		}
    		
    		return null;
    	}
    	/* Warp to a given player. */
    	else if(command.equals("warpto")){
    		if(args.length != 1){
    			player.sendUsage("warpto <player>");
    			
    			return null;
    		}
    		
    		SpMcPlayer target = player.getWrapper().getPlayers().get(args[0]);
    		
    		if(target != null){
    			this.warp(player, target);
    			
    			player.sendMessage(
    				COLOR_LIGHT_GREEN, "Warped to %s%s!",
    				target.getDisplayName(), COLOR_LIGHT_GREEN
    			);
    		}
    		else{
    			player.sendMessage(COLOR_LIGHT_RED, "That player is not online!");
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
		
		/* Fill the packet with position and look information. */
		packet.x        = player.getPosition().x;
		packet.z        = player.getPosition().z;
		packet.yaw      = player.getLook().yaw;
		packet.pitch    = player.getLook().pitch;
		packet.onGround = false;
		
		/* First move up to level 128. */
		packet.y      = 128.0;
		packet.stance = 128.0 + 1.62;
		
		player.sendToServer(packet);
		
		/* Then move sideways to the target location. */
		packet.x = target.x;
		packet.z = target.z;
		
		player.sendToServer(packet);
		
		/* Now move down to the target level. */
		packet.y        = target.y;
		packet.stance   = target.y + 1.62;
		packet.onGround = true;
		
		player.sendToServer(packet);
		
		/*
		 * Also send that packet to the client, but swap
		 * Y and stance here... (see protocol specification).
		 */
		packet.y      = target.y + 1.62;
		packet.stance = target.y;
		
		player.sendToClient(packet);
	}
}
