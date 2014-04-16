package hu.spykeh.toho.net.packets;

import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;

public class Packet03Shoot extends Packet {

	private String username;
	private boolean shooting;

	public Packet03Shoot(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.shooting = Boolean.parseBoolean(dataArray[1]);
	}

	public Packet03Shoot(String username, boolean shooting) {
		super(03);
		this.username = username;
		this.shooting = shooting;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("03" + username + "," + shooting).getBytes();
	}

	public String getUserName() {
		return username;
	}
	
	public boolean isShooting(){
		return shooting;
	}


}
