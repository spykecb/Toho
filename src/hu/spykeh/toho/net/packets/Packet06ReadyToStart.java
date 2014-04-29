package hu.spykeh.toho.net.packets;

import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;

public class Packet06ReadyToStart extends Packet {
	private String username;
	public Packet06ReadyToStart(byte[] data) {
		super(06);
		this.username = readData(data);

	}
	
	public Packet06ReadyToStart(String username) {
		super(06);
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
		return ("06" + this.username).getBytes();
	}
	
	public String getUserName(){
		return username;
	}
	
}
