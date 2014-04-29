package hu.spykeh.toho.net.packets;

import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;

public abstract class Packet {

	public static enum PacketTypes{
		INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02), SHOOT(03), MOBSPAWN(04), READY(05), READYTOSTART(06);
		
		private int packetId;
		
		private PacketTypes(int packetId){
			this.packetId = packetId;
		}
		
		public int getId(){
			return packetId;
		}
	}
	
	public byte packetId;
	
	public Packet(int packetId){
		this.packetId = (byte) packetId;
	}
	
	public abstract void writeData(GameClient client); //adott kliens üzen a szervernek
	public abstract void writeData(GameServer server); //a szerver üzen a klienseknek
	public abstract byte[] getData();
	
	public String readData(byte[] data){
		String message = new String(data).trim();
		return message.substring(2);
	}
	
	
	/*
	 * visszaadja a csomag tipusát egy szám segitségével
	 */
	public static PacketTypes lookupPackets(String packetId){
		try{
			return lookupPackets(Integer.parseInt(packetId));
		}catch(NumberFormatException e){
			return PacketTypes.INVALID;
		}
	}
	public static PacketTypes lookupPackets(int id){
		for(PacketTypes p : PacketTypes.values()){
			if(id == p.getId()){
				return p;
			}
		}
		return PacketTypes.INVALID;
	}
}
