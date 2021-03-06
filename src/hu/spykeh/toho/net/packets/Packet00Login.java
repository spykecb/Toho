package hu.spykeh.toho.net.packets;

import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;

public class Packet00Login extends Packet{

	private String username;
	private int x,y;
	private int side;
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];;
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.side = Integer.parseInt(dataArray[3]);
	}
	
	public Packet00Login(String username, int x, int y, int side) {
		super(00);
		this.x = x;
		this.y = y;
		this.username = username;
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
		return ("00" + this.username + "," + this.x + "," + this.y + "," + this.side).getBytes();
	}
	
	public String getUserName(){
		return username;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int getSide(){
		return side;
	}

}
