package de.felixbruns.minecraft.protocol;

public class Look {
	public float yaw;
	public float pitch;
	
	public Look(){
		this(0.0f, 0.0f);
	}
	
	public Look(float yaw, float pitch){
		this.yaw   = yaw;
		this.pitch = pitch;
	}
	
	public Look copy(){
		return new Look(this.yaw, this.pitch);
	}
	
	public String toString(){
		return String.format("(%.2f | %.2f)", this.yaw, this.pitch);
	}
}
