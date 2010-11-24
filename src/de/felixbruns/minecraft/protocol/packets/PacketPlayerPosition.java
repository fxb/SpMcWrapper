package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x0B, type = "Client", name = "PlayerPosition")
public class PacketPlayerPosition extends Packet {
	@ProtocolField(name = "X")
	public double x;
	
	@ProtocolField(name = "Y")
	public double y;
	
	@ProtocolField(name = "Stance")
	public double stance;
	
	@ProtocolField(name = "Z")
	public double z;
	
	@ProtocolField(name = "On Ground")
	public boolean onGround;
	
	public Position getPosition(){
		return new Position(this.x, this.y, this.z);
	}
	
	public String toString(){
		return this.getPosition().toString();
	}
}
