package hu.spykeh.toho.net.packets;

import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;

public class Packet04MobSpawn extends Packet{
	private String name;
	private int x,y;
	private int hp;
	private int side;
	public Packet04MobSpawn(byte[] data) {
		super(04);
		String[] dataArray = readData(data).split(",");
		this.name = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.hp = Integer.parseInt(dataArray[3]);
		this.side = Integer.parseInt(dataArray[4]);
	}
	
	public Packet04MobSpawn(String name, int x, int y, int hp, int side) {
		super(04);
		this.x = x;
		this.y = y;
		this.name = name;
		this.hp = hp;
		this.side = side;
	}

	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("04" + this.name + "," + this.x + "," + this.y + "," + this.hp + "," + this.side).getBytes();
	}
	
	public String getName(){
		return name;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int getHP(){
		return hp;
	}
	public int getSide(){
		return side;
	}
}
