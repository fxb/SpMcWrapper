package de.felixbruns.minecraft.protocol.packets;

import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket.Direction;

@ProtocolPacket(id = 0x26, direction = Direction.SERVER_CLIENT, name = "EntitySound")
public class PacketEntitySound extends Packet {
	@ProtocolField(name = "Entity Id")
	public int eid;
	
	@ProtocolField(name = "Type")
	public byte type; /* 0x02: Entity was hurt, 0x03: Entity died, 0x04: Creeper starts flashing. */
}
