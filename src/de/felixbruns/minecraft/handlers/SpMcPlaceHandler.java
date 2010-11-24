package de.felixbruns.minecraft.handlers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.felixbruns.minecraft.SpMcPlayer;
import de.felixbruns.minecraft.protocol.ChatColors;
import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketBlockChange;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerBlockPlacement;

public class SpMcPlaceHandler extends SpMcCommandHandler implements ChatColors {
    /**
     * Handle a place command sent by a player.
     * 
     * @param player   The associated player.
     * @param command  The command that was sent.
     * @param args     The arguments to the command.
     */
    public Packet handleCommand(SpMcPlayer player, Packet packet, String command, String... args){
    	if(command.equals("place")){
    		BufferedImage image = null;
    		
    		try {
				image = ImageIO.read(new File("image.png"));
			}
    		catch(IOException e){
				e.printStackTrace();
			}
    		
    		PacketBlockChange          bc   = new PacketBlockChange();
    		PacketPlayerBlockPlacement pbp  = new PacketPlayerBlockPlacement();
    		byte                       item = 0;
    		
    		for(int x = 0; x < image.getWidth(); x++){
        		for(int y = 0; y < image.getHeight(); y++){
        			int pixel = image.getRGB(x, image.getHeight() - y - 1);
        			int a     = (pixel & 0xff000000) >> 24;
        			int r     = (pixel & 0x00ff0000) >> 16;
        			int g     = (pixel & 0x0000ff00) >>  8;
        			int b     = (pixel & 0x000000ff);
        			
        			if(a == 0){
        				continue;
        			}
        			
        			if(r == 0 && g == 0 && b == 0){
        				item = 49; //Obsidian
        			}
        			
	    			if(r == 255 && g == 255 && b == 255){
	    				item = 80; //Snow
	    			}
	    			
	    			if(r == 255 && g == 0 && b == 0){
	    				item = 45; //Brick
	    			}
	    			
	    			if(r == 0 && g == 255 && b == 0){
	    				item = 18; //Leaves
	    			}

	    			if(r == 0 && g == 0 && b == 255){
	    				item = 57; //Diamond
	    			}
	    			
	    			if(r == 255 && g == 255 && b == 0){
	    				item = 41; //Gold
	    			}
	    			
	    			if(r == 255 && g == 127 && b == 0){
	    				item = 86; //Pumpkin
	    			}
        			
            		bc.x        = (int) player.getPosition().x + x;
                	bc.y        = (byte)(player.getPosition().y + 5 + y);
                    bc.z        = (int) player.getPosition().z;
            		bc.type     = item;
            		bc.metadata = 0;
            		
            		pbp.x         = (int) player.getPosition().x + x;
            		pbp.y         = (byte)(player.getPosition().y + 5 + y);
            		pbp.z         = (int) player.getPosition().z;
            		pbp.id        = item;
            		pbp.direction = 0;
            		
            		player.sendToClient(bc);
            		player.sendToServer(pbp);
        		}
    		}
    		
    		return null;
    	}
    	
    	return packet;
    }
}
