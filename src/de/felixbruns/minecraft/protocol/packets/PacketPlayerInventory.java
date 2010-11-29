package de.felixbruns.minecraft.protocol.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.felixbruns.minecraft.protocol.Item;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolReadHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolWriteHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x05, direction = Direction.BIDIRECTIONAL, name = "PlayerInventory")
public class PacketPlayerInventory extends Packet {
	public static final int TYPE_MAIN_INVENTORY = -1;
	public static final int TYPE_EQUIPPED_ARMOR = -2;
	public static final int TYPE_CRAFTING_SLOTS = -3;
	
	@ProtocolField(name = "Type")
	public int type;
	
	@ProtocolField(name = "Count")
	public short count;
	
	@ProtocolField(name = "Items")
	public List<Item> items;
	
	@ProtocolReadHelper(name = "Items")
	public void readItems(DataInputStream stream) throws IOException {
		this.items = new ArrayList<Item>();
		
		for(int slot = 0; slot < this.count; slot++){
			Item item = new Item();
			
			item.id = stream.readShort();
            
			if(item.id != -1){
				item.count  = stream.readByte();
				item.health = stream.readShort();
			}
			
			this.items.add(item);
		}
	}
	
	@ProtocolWriteHelper(name = "Items")
	public void writeItems(DataOutputStream stream) throws IOException {
		for(Item item : this.items){
			stream.writeShort(item.id);
			
			if(item.id != -1){
				stream.writeByte(item.count);
				stream.writeShort(item.health);
			}
		}
	}
}
