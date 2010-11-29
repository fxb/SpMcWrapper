package de.felixbruns.minecraft.protocol.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolReadHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolWriteHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x33, direction = Direction.SERVER_CLIENT, name = "MapChunk")
public class PacketMapChunk extends Packet {
	@ProtocolField(name = "Block X")
	public int x;
	
	@ProtocolField(name = "Block Y")
	public short y;
	
	@ProtocolField(name = "Block Z")
	public int z;
	
	@ProtocolField(name = "Size X")
	public byte sizeX;
	
	@ProtocolField(name = "Size Y")
	public byte sizeY;
	
	@ProtocolField(name = "Size Z")
	public byte sizeZ;
	
	@ProtocolField(name = "Data Size")
	public int size;
	
	@ProtocolField(name = "Data")
	public byte[] data;
	
	@ProtocolReadHelper(name = "Data")
	public void readData(DataInputStream stream) throws IOException {
		this.data = new byte[this.size];
		
		int read = 0;
		
		while(read < this.size){
			read += stream.read(this.data, read, this.size - read);
		}
	}
	
	@ProtocolWriteHelper(name = "Data")
	public void writeData(DataOutputStream stream) throws IOException {
		stream.write(this.data);
	}
}
