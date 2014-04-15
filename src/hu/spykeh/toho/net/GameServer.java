package hu.spykeh.toho.net;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.net.packets.Packet;
import hu.spykeh.toho.net.packets.Packet.PacketTypes;
import hu.spykeh.toho.net.packets.Packet00Login;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {
	private DatagramSocket socket;
	private Jatek jatek;
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

	public GameServer(Jatek jatek) {
		try {
			this.socket = new DatagramSocket(1551);
		} catch (SocketException e) {

			e.printStackTrace();
		}

		this.jatek = jatek;

	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			/*
			 * String message = new String(packet.getData());
			 * System.out.println(
			 * "CLIENT["+packet.getAddress().getHostAddress()+
			 * ":"+packet.getPort()+"] > " + message);
			 */

		}
	}

	/*
	 * Kap egy adatot, megállapitja a tipusát, majd ennek megfelelõen elvégzi
	 * vele a dolgát.
	 */
	public void parsePacket(byte[] data, InetAddress ip, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPackets(message.substring(0, 2));
		Packet packet = null;
		switch (type) {
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
					+ ((Packet00Login)packet).getUserName() + " connected...");
			PlayerMP player = new PlayerMP(jatek.level, ((Packet00Login)packet).getUserName(), 100, 100, ip, port);
			this.addConnection(player, ((Packet00Login)packet));
			break;
		case DISCONNECT:
			break;
		default:
		}
	}

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		for (PlayerMP p : connectedPlayers) {
			if (player.getUsername().equalsIgnoreCase(p.getUsername())) { 
				if (p.ip == null) {
					p.ip = player.ip;
				}
				if (p.port == -1) {
					p.port = player.port;
				}
				alreadyConnected = true;
			} else { 
				sendData(packet.getData(), p.ip, p.port); //küldjük el az online játékosoknak, hogy belépett
				
				packet = new Packet00Login(p.getUsername());
				sendData(packet.getData(), player.ip, player.port);
			}

		}
		if(!alreadyConnected){
			this.connectedPlayers.add(player);
		}

	}

	public void sendData(byte[] data, InetAddress ip, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : connectedPlayers) {
			sendData(data, p.ip, p.port);
		}
	}
}
