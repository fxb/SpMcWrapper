package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x11, direction = Direction.SERVER_CLIENT, name = "AddToInventory")
public class PacketAddToInventory extends Packet {
	public static final int TYPE_MAIN_INVENTORY = -1;
	public static final int TYPE_EQUIPPED_ARMOR = -2;
	public static final int TYPE_CRAFTING_SLOTS = -3;
	
	@ProtocolField(name = "Item")
	public short item;
	
	@ProtocolField(name = "Count")
	public byte count;
	
	@ProtocolField(name = "Life")
	public short life;
}
