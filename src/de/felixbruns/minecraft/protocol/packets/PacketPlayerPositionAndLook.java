package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.Position;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;

@ProtocolPacket(id = 0x0D, type = "Client & Server", name = "Player Position & Look")
public class PacketPlayerPositionAndLook extends Packet {
	@ProtocolField(name = "X")
	public double x;
	
	@ProtocolField(name = "Y")
	public double y;
	
	@ProtocolField(name = "Stance")
	public double stance;
	
	@ProtocolField(name = "Z")
	public double z;
	
	@ProtocolField(name = "Yaw")
	public float yaw;
	
	@ProtocolField(name = "Pitch")
	public float pitch;
	
	@ProtocolField(name = "On Ground")
	public boolean onGround;
	
	public Position getPosition(){
		return new Position(this.x, this.y, this.z);
	}
	
	public String toString(){
		return this.getPosition().toString();
	}
}
