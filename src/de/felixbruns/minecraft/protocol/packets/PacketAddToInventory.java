package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x11, type = "Server", name = "AddToInventory")
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
