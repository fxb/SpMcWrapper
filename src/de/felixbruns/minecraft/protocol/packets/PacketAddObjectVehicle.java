package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x17, direction = Direction.SERVER_CLIENT, name = "AddObjectVehicle")
public class PacketAddObjectVehicle extends Packet {
	public static final byte TYPE_BOAT             = 1;
	public static final byte TYPE_MINECART         = 10;
	public static final byte TYPE_MINECART_STORAGE = 11;
	public static final byte TYPE_MINECART_POWERED = 12;
	
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "Type")
	public byte type;
	
	@ProtocolField(name = "X")
	public int x;
	
	@ProtocolField(name = "Y")
	public int y;
	
	@ProtocolField(name = "Z")
	public int z;
}
