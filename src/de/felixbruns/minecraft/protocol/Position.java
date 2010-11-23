package de.felixbruns.minecraft.protocol;

public class Position {
	public double x;
	public double y;
	public double z;
	
	public Position(){
		this(0.0, 0.0, 0.0);
	}
	
	public Position(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double distance(Position p){
		return Math.sqrt(
			Math.pow(this.x - p.x, 2) +
			Math.pow(this.y - p.y, 2) +
			Math.pow(this.z - p.z, 2)
		);
	}
	
	public Position copy(){
		return new Position(this.x, this.y, this.z);
	}
	
	public String toString(){
		return String.format("(%.2f | %.2f | %.2f)", this.x, this.y, this.z);
	}
}
