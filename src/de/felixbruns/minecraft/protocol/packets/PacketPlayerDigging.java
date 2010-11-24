package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x0E, type = "Client", name = "PlayerDigging")
public class PacketPlayerDigging extends Packet {
	public static final byte STATUS_STARTED_DIGGING = 0;
	public static final byte STATUS_DIGGING         = 1;
	public static final byte STATUS_STOPPED_DIGGING = 2;
	public static final byte STATUS_BLOCK_BROKEN    = 3;
	
	public static final byte FACE_MINUS_Y = 0;
	public static final byte FACE_PLUS_Y  = 1;
	public static final byte FACE_MINUS_Z = 2;
	public static final byte FACE_PLUS_Z  = 3;
	public static final byte FACE_MINUS_X = 4;
	public static final byte FACE_PLUS_X  = 5;
	
	@ProtocolField(name = "Status")
	public byte status;
	
	@ProtocolField(name = "X")
	public int x;
	
	@ProtocolField(name = "Y")
	public byte y;
	
	@ProtocolField(name = "Z")
	public int z;
	
	@ProtocolField(name = "Face")
	public byte face;
}
