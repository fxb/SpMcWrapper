package de.felixbruns.minecraft.protocol.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
	
	@ProtocolField(name = "Coordinate Array")
	public short[] coordinates;
	
	@ProtocolField(name = "Type Array")
	public byte[] types;
	
	@ProtocolField(name = "Metadata Array")
	public byte[] metadata;
	
	@ProtocolReadHelper(name = "Coordinate Array")
	public void readCoordinates(DataInputStream stream) throws IOException {
		this.coordinates = new short[this.size];
		
		for(int i = 0; i < this.size; i++){
			this.coordinates[i] = stream.readShort();
		}
	}
	
	@ProtocolWriteHelper(name = "Coordinate Array")
	public void writeCoordinates(DataOutputStream stream) throws IOException {
		for(int i = 0; i < this.size; i++){
			stream.writeShort(this.coordinates[i]);
		}
	}
	
	@ProtocolReadHelper(name = "Type Array")
	public void readTypes(DataInputStream stream) throws IOException {
		this.types = new byte[this.size];
		
		stream.read(this.types);
	}
	
	@ProtocolWriteHelper(name = "Type Array")
	public void writeTypes(DataOutputStream stream) throws IOException {
		stream.write(this.types);
	}
	
	@ProtocolReadHelper(name = "Metadata Array")
	public void readMetadata(DataInputStream stream) throws IOException {
		this.metadata = new byte[this.size];
		
		stream.read(this.metadata);
	}
	
	@ProtocolWriteHelper(name = "Metadata Array")
	public void writeMetadata(DataOutputStream stream) throws IOException {
		stream.write(this.metadata);
	}
}
