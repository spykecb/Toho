package hu.spykeh.toho.net.packets;

import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;

public class Packet01Disconnect extends Packet {

	private String username;
	public Packet01Disconnect(byte[] data) {
		super(00);
		this.username = readData(data);
	}
	
	public Packet01Disconnect(String username) {
		super(01);
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
		return ("01" + this.username).getBytes();
	}
	
	public String getUserName(){
		return username;
	}

}
