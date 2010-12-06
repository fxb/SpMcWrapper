package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;
import de.felixbruns.minecraft.util.ItemOrBlock;

@ProtocolPacket(id = 0x0F, direction = Direction.CLIENT_SERVER, name = "PacketPlayerRightClick")
public class PacketPlayerRightClick extends Packet {
	public static final byte DIRECTION_MINUS_Y = 0;
	public static final byte DIRECTION_PLUS_Y  = 1;
	public static final byte DIRECTION_MINUS_Z = 2;
	public static final byte DIRECTION_PLUS_Z  = 3;
	public static final byte DIRECTION_MINUS_X = 4;
	public static final byte DIRECTION_PLUS_X  = 5;
	
	public PacketPlayerRightClick(){
		this(ItemOrBlock.UNDEFINED);
	}
	
	public PacketPlayerRightClick(ItemOrBlock item){
		this.id        = item.getId();
		this.x         = -1;
		this.y         = -1;
		this.z         = -1;
		this.direction = -1;
	}
	
	@ProtocolField(name = "Block or Item ID")
	public short id;
	
	@ProtocolField(name = "X")
	public int x;
	
	@ProtocolField(name = "Y")
	public byte y;
	
	@ProtocolField(name = "Z")
	public int z;
	
	@ProtocolField(name = "Direction")
	public byte direction;
}
