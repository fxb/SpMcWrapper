package de.felixbruns.minecraft.protocol;

import java.util.HashMap;
import java.util.Map;

import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.PacketAddObjectVehicle;
import de.felixbruns.minecraft.protocol.packets.PacketAddToInventory;
import de.felixbruns.minecraft.protocol.packets.PacketAnimate;
import de.felixbruns.minecraft.protocol.packets.PacketAttachEntity;
import de.felixbruns.minecraft.protocol.packets.PacketBlockChange;
import de.felixbruns.minecraft.protocol.packets.PacketChatMessage;
import de.felixbruns.minecraft.protocol.packets.PacketCollectItem;
import de.felixbruns.minecraft.protocol.packets.PacketComplexEntities;
import de.felixbruns.minecraft.protocol.packets.PacketDestroyEntity;
import de.felixbruns.minecraft.protocol.packets.PacketDisconnect;
import de.felixbruns.minecraft.protocol.packets.PacketEntity;
import de.felixbruns.minecraft.protocol.packets.PacketEntityLook;
import de.felixbruns.minecraft.protocol.packets.PacketEntityRelativeMove;
import de.felixbruns.minecraft.protocol.packets.PacketEntityRelativeMoveAndLook;
import de.felixbruns.minecraft.protocol.packets.PacketEntityTeleport;
import de.felixbruns.minecraft.protocol.packets.PacketEntityVelocity;
import de.felixbruns.minecraft.protocol.packets.PacketHandshake;
import de.felixbruns.minecraft.protocol.packets.PacketEntityDie;
import de.felixbruns.minecraft.protocol.packets.PacketHoldingChange;
import de.felixbruns.minecraft.protocol.packets.PacketKeepAlive;
import de.felixbruns.minecraft.protocol.packets.PacketLogin;
import de.felixbruns.minecraft.protocol.packets.PacketMapChunk;
import de.felixbruns.minecraft.protocol.packets.PacketMobSpawn;
import de.felixbruns.minecraft.protocol.packets.PacketMultiBlockChange;
import de.felixbruns.minecraft.protocol.packets.PacketNamedEntitySpawn;
import de.felixbruns.minecraft.protocol.packets.PacketPickupSpawn;
import de.felixbruns.minecraft.protocol.packets.PacketPlayer;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerBlockPlacement;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerDigging;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerHealth;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerInventory;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerLook;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPosition;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerPositionAndLook;
import de.felixbruns.minecraft.protocol.packets.PacketPlayerRespawn;
import de.felixbruns.minecraft.protocol.packets.PacketPreChunk;
import de.felixbruns.minecraft.protocol.packets.PacketSpawnPosition;
import de.felixbruns.minecraft.protocol.packets.PacketTimeUpdate;
import de.felixbruns.minecraft.protocol.packets.PacketUseEntity;


public class Packets {
	public static final Map<Integer, Class<? extends Packet>> PACKETS = new HashMap<Integer, Class<? extends Packet>>(){
        private static final long serialVersionUID = 1L;
        
        {
    		put(0x00, PacketKeepAlive.class);
        	put(0x01, PacketLogin.class);
        	put(0x02, PacketHandshake.class);
        	put(0x03, PacketChatMessage.class);
        	put(0x04, PacketTimeUpdate.class);
        	put(0x05, PacketPlayerInventory.class);
        	put(0x06, PacketSpawnPosition.class);
        	put(0x07, PacketUseEntity.class);
        	put(0x08, PacketPlayerHealth.class);
        	put(0x09, PacketPlayerRespawn.class);
        	put(0x0A, PacketPlayer.class);
        	put(0x0B, PacketPlayerPosition.class);
        	put(0x0C, PacketPlayerLook.class);
        	put(0x0D, PacketPlayerPositionAndLook.class);
        	put(0x0E, PacketPlayerDigging.class);
        	put(0x0F, PacketPlayerBlockPlacement.class);
        	put(0x10, PacketHoldingChange.class);
        	put(0x11, PacketAddToInventory.class);
        	put(0x12, PacketAnimate.class);
        	put(0x14, PacketNamedEntitySpawn.class);
        	put(0x15, PacketPickupSpawn.class);
        	put(0x16, PacketCollectItem.class);
        	put(0x17, PacketAddObjectVehicle.class);
        	put(0x18, PacketMobSpawn.class);
        	put(0x1C, PacketEntityVelocity.class);
        	put(0x1D, PacketDestroyEntity.class);
        	put(0x1E, PacketEntity.class);
        	put(0x1F, PacketEntityRelativeMove.class);
        	put(0x20, PacketEntityLook.class);
        	put(0x21, PacketEntityRelativeMoveAndLook.class);
        	put(0x22, PacketEntityTeleport.class);
        	put(0x26, PacketEntityDie.class);
        	put(0x27, PacketAttachEntity.class);
        	put(0x32, PacketPreChunk.class);
        	put(0x33, PacketMapChunk.class);
        	put(0x34, PacketMultiBlockChange.class);
        	put(0x35, PacketBlockChange.class);
        	put(0x3B, PacketComplexEntities.class);
        	put(0xFF, PacketDisconnect.class);
    	}
	};
	
	public static Class<? extends Packet> getPacketClassById(int id){
		return PACKETS.get(id);
	}
}
