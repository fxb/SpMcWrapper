package de.felixbruns.minecraft.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import de.felixbruns.minecraft.protocol.packets.Packet;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolField;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolPacket;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolReadHelper;
import de.felixbruns.minecraft.protocol.packets.annotations.ProtocolWriteHelper;

public class PacketStream {
	private DataInputStream  input;
	private DataOutputStream output;
	
	public PacketStream(Socket socket) throws IOException {
		this.input  = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
	}
	
	public void close(){
		try {
	        this.input.close();
	        this.output.close();
        }
        catch(IOException e){
        	/* Ignore. */
        }
	}
	
	public Packet read() throws IOException {
		synchronized(this.input){
			int                     id     = this.input.readByte() & 0xFF;
			Class<? extends Packet> clazz  = PacketFinder.getPacketClassById(id);
			Packet                  packet = null;
			
			if(clazz == null){
				throw new RuntimeException("Packet class for id '" + id + "' not found!");
			}
			
			try {
				packet = clazz.newInstance();
				
				for(Field field : clazz.getFields()){
					ProtocolField pf = (ProtocolField)field.getAnnotation(ProtocolField.class);
					
					if(pf == null){
						continue;
					}
					
					Class<?> type = field.getType();
					
					if(type.equals(Byte.class) || type.equals(byte.class)){
						field.setByte(packet, this.input.readByte());
					}
					else if(type.equals(Short.class) || type.equals(short.class)){
						field.setShort(packet, this.input.readShort());
					}
					else if(type.equals(Integer.class) || type.equals(int.class)){
						field.setInt(packet, this.input.readInt());
					}
					else if(type.equals(Long.class) || type.equals(long.class)){
						field.setLong(packet, this.input.readLong());
					}
					else if(type.equals(Float.class) || type.equals(float.class)){
						field.setFloat(packet, this.input.readFloat());
					}
					else if(type.equals(Double.class) || type.equals(double.class)){
						field.setDouble(packet, this.input.readDouble());
					}
					else if(type.equals(Boolean.class) || type.equals(boolean.class)){
						int value = this.input.read();
						
						if(value == 0x00){
							field.setBoolean(packet, false);
						}
						else if(value == 0x01){
							field.setBoolean(packet, true);
						}
						else{
							System.out.println("WARNING! Reading boolean but got value '" + value + "'!");
							
							field.setBoolean(packet, true);
						}
					}
					else if(type.equals(String.class)){
						field.set(packet, this.input.readUTF());
					}
					else{
						boolean handled = false;
						
						for(Method method : clazz.getMethods()){
							ProtocolReadHelper helper = (ProtocolReadHelper)method.getAnnotation(ProtocolReadHelper.class);
							
							if(helper != null && helper.name().equals(pf.name())){
								method.invoke(packet, this.input);
								
								handled = true;
								
								break;
							}
						}
						
						if(!handled){
							throw new RuntimeException("@ProtocolReadHelper for protocol field '" + pf.name() + "' not found!");
						}
					}
				}
			}
	        catch(IllegalAccessException e){
				throw new RuntimeException(e);
			}
			catch(InstantiationException e){
				throw new RuntimeException(e);
			}
			catch(InvocationTargetException e){
				throw new RuntimeException(e);
			}
			
			return packet;
		}
	}
	
	public void write(Packet packet) throws IOException {
		synchronized(this.output){
			Class<?>       clazz          = packet.getClass();
			ProtocolPacket protocolPacket = (ProtocolPacket)clazz.getAnnotation(ProtocolPacket.class);
			
			if(protocolPacket == null){
				throw new RuntimeException("@ProtocolPacket annotation not found!");
			}
			
			this.output.writeByte(protocolPacket.id());
			
			try {
				for(Field field : clazz.getFields()){
					ProtocolField pf = (ProtocolField)field.getAnnotation(ProtocolField.class);
					
					if(pf == null){
						continue;
					}
					
					Class<?> type = field.getType();
					
					if(type.equals(Byte.class) || type.equals(byte.class)){
						this.output.writeByte(field.getByte(packet));
					}
					else if(type.equals(Short.class) || type.equals(short.class)){
						this.output.writeShort(field.getShort(packet));
					}
					else if(type.equals(Integer.class) || type.equals(int.class)){
						this.output.writeInt(field.getInt(packet));
					}
					else if(type.equals(Long.class) || type.equals(long.class)){
						this.output.writeLong(field.getLong(packet));
					}
					else if(type.equals(Float.class) || type.equals(float.class)){
						this.output.writeFloat(field.getFloat(packet));
					}
					else if(type.equals(Double.class) || type.equals(double.class)){
						this.output.writeDouble(field.getDouble(packet));
					}
					else if(type.equals(Boolean.class) || type.equals(boolean.class)){
						this.output.writeBoolean(field.getBoolean(packet));
					}
					else if(type.equals(String.class)){
						this.output.writeUTF((String)field.get(packet));
					}
					else{
						boolean handled = false;
						
						for(Method method : clazz.getMethods()){
							ProtocolWriteHelper helper = (ProtocolWriteHelper)method.getAnnotation(ProtocolWriteHelper.class);
							
							if(helper != null && helper.name().equals(pf.name())){
								method.invoke(packet, this.output);
								
								handled = true;
								
								break;
							}
						}
						
						if(!handled){
							throw new RuntimeException("@ProtocolWriteHelper for protocol field '" + pf.name() + "' not found!");
						}
					}
				}
			}
			catch(IllegalAccessException e){
				throw new RuntimeException(e);
			}
	        catch(InvocationTargetException e){
				throw new RuntimeException(e);
	        }
	        
	        this.output.flush();
		}
	}
}
