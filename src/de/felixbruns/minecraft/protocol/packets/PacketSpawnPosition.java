package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x06, direction = Direction.SERVER_CLIENT, name = "SpawnPosition")
public class PacketSpawnPosition extends Packet {
	@ProtocolField(name = "X")
	public int x;
	
	@ProtocolField(name = "Y")
	public int y;
	
	@ProtocolField(name = "Z")
	public int z;
	
	public String toString(){
		return String.format(
			"%s: %d, %d, %d",
			this.getClass().getName(),
			this.x, this.y, this.z
		);
	}
}
