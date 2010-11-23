package de.felixbruns.minecraft.protocol.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.felixbruns.minecraft.protocol.Block;
import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolReadHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolWriteHelper;

@ProtocolPacket(id = 0x34, type = "Server", name = "Multi Block Change")
public class PacketMultiBlockChange extends Packet {
	@ProtocolField(name = "Chunk X")
	public int x;
	
	@ProtocolField(name = "Chunk Z")
	public int z;
	
	@ProtocolField(name = "Size")
	public short size;
	
	@ProtocolField(name = "Blocks")
	public List<Block> blocks;
	
	@ProtocolReadHelper(name = "Blocks")
	public void readBlocks(DataInputStream stream) throws IOException {
		this.blocks = new ArrayList<Block>(this.size);
		
		for(int i = 0; i < this.size; i++){
			this.blocks.add(new Block());
		}
		
		for(int i = 0; i < this.size; i++){
			short coordinates = stream.readShort();
			
			this.blocks.get(i).position = new Position(
				(coordinates & 0xF000) >> 12,
				(coordinates & 0x00FF),
				(coordinates & 0x0F00) >>  8
			);
		}
		
		for(int i = 0; i < this.size; i++){
			this.blocks.get(i).type = stream.readByte();
		}
		
		for(int i = 0; i < this.size; i++){
			this.blocks.get(i).metadata = stream.readByte();
		}
	}
	
	@ProtocolWriteHelper(name = "Blocks")
	public void writeBlocks(DataOutputStream stream) throws IOException {
		for(int i = 0; i < this.size; i++){
			Position p = this.blocks.get(i).position;
			
			stream.writeShort(
				((short)p.x << 12) |
				((short)p.z <<  8) |
				((short)p.y)
			);
		}
		
		for(int i = 0; i < this.size; i++){
			stream.write(this.blocks.get(i).type);
		}
		
		for(int i = 0; i < this.size; i++){
			stream.write(this.blocks.get(i).metadata);
		}
	}
}
