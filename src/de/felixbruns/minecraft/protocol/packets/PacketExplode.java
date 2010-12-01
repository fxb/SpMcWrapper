package de.felixbruns.minecraft.protocol.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolReadHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolWriteHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x3C, direction = Direction.SERVER_CLIENT, name = "Explode")
public class PacketExplode extends Packet {
	@ProtocolField(name = "X")
	public double x;
	
	@ProtocolField(name = "Y")
	public double y;
	
	@ProtocolField(name = "Z")
	public double z;
	
	@ProtocolField(name = "Unknown")
	public float unknown;
	
	@ProtocolField(name = "Size")
	public short size;
	
	@ProtocolField(name = "Coordinates")
	public List<Position> coordinates;
	
	@ProtocolReadHelper(name = "Coordinates")
	public void readBlocks(DataInputStream stream) throws IOException {
		this.coordinates = new ArrayList<Position>(this.size);
		
		for(int i = 0; i < this.size; i++){
			this.coordinates.add(new Position(
				stream.readByte() + (int)this.x,
				stream.readByte() + (int)this.y,
				stream.readByte() + (int)this.z
			));
		}
	}
	
	@ProtocolWriteHelper(name = "Coordinates")
	public void writeBlocks(DataOutputStream stream) throws IOException {
		for(int i = 0; i < this.size; i++){
			Position p = this.coordinates.get(i);
			
			stream.writeByte((int)p.x - (int)this.x);
			stream.writeByte((int)p.y - (int)this.y);
			stream.writeByte((int)p.z - (int)this.z);
		}
	}
}
