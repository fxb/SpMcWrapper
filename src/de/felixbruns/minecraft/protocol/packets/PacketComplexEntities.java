package de.felixbruns.minecraft.protocol.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolReadHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolWriteHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x3B, direction = Direction.SERVER_CLIENT, name = "ComplexEntities")
public class PacketComplexEntities extends Packet {
	@ProtocolField(name = "Entity X")
	public int x;
	
	@ProtocolField(name = "Entity")
	public short y;
	
	@ProtocolField(name = "Entity Z")
	public int z;
	
	@ProtocolField(name = "Size")
	public short size;
	
	@ProtocolField(name = "Data")
	public byte[] data;
	
	@ProtocolReadHelper(name = "Data")
	public void readData(DataInputStream stream) throws IOException {
		this.data = new byte[this.size & 0xffff];
		
		stream.readFully(this.data);
	}
	
	@ProtocolWriteHelper(name = "Data")
	public void writeData(DataOutputStream stream) throws IOException {
		stream.write(this.data);
	}
}
