package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x01, type = "Client & Server", name = "Login")
public class PacketLogin extends Packet {
	public static final byte DIMENSION_HELL   = -1;
	public static final byte DIMENSION_NORMAL =  0;
	
	@ProtocolField(name = "Protocol Version or Player Entity ID")
	public int versionOrEid;
	
	@ProtocolField(name = "Username")
	public String username;
	
	@ProtocolField(name = "Password")
	public String password;
	
	@ProtocolField(name = "Map Seed")
	public long mapSeed;
	
	@ProtocolField(name = "Dimension")
	public byte dimension;
}
