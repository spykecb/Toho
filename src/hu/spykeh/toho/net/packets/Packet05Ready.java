package hu.spykeh.toho.net.packets;

import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;

public class Packet05Ready extends Packet{
	private String username;
	public Packet05Ready(byte[] data) {
		super(05);
		this.username = readData(data);

	}
	
	public Packet05Ready(String username) {
		super(05);
		this.username = username;
	}

	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("05" + this.username).getBytes();
	}
	
	public String getUserName(){
		return username;
	}
	
}
