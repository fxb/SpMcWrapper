package de.felixbruns.minecraft.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.util.ClassFinder;

public class PacketFinder {
	public static final List<Class<? extends Packet>>         packetList;
	public static final Map<Integer, Class<? extends Packet>> packetMap;
	
	static {
		List<Class<?>> classes = ClassFinder.getClasses("de.felixbruns.minecraft.protocol.packets");
		
		packetList = new ArrayList<Class<? extends Packet>>();
		packetMap  = new HashMap<Integer, Class<? extends Packet>>();
		
		for(Class<?> clazz : classes){
			ProtocolPacket protocolPacket = clazz.getAnnotation(ProtocolPacket.class);
			
			if(protocolPacket != null && !protocolPacket.direction().isUnspecified()){
				Class<? extends Packet> packetClass = clazz.asSubclass(Packet.class);
				
				packetList.add(packetClass);
				packetMap.put(protocolPacket.id() & 0xff, packetClass);
			}
		}
	}
	
	public static List<Class<? extends Packet>> getPacketClasses(){
		return packetList;
	}
	
	public static Class<? extends Packet> getPacketClassById(int id){
		return packetMap.get(id);
	}
}
