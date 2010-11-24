package de.felixbruns.minecraft.protocol.packets.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface ProtocolPacket {
	public byte      id();
	public Direction direction();
	public String    name();
	
	public enum Direction {
		UNSPECIFIED,
		BIDIRECTIONAL,
		CLIENT_SERVER,
		SERVER_CLIENT;
		
		public boolean isUnspecified(){
			return this.equals(UNSPECIFIED);
		}
		
		public boolean isBidirectional(){
			return this.equals(BIDIRECTIONAL);
		}
		
		public boolean isClientPacket(){
			return this.equals(CLIENT_SERVER) || this.equals(BIDIRECTIONAL);
		}
		
		public boolean isServerPacket(){
			return this.equals(SERVER_CLIENT) || this.equals(BIDIRECTIONAL);
		}
	}
}
