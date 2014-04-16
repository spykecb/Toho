package hu.spykeh.toho.net;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.net.packets.Packet;
import hu.spykeh.toho.net.packets.Packet00Login;
import hu.spykeh.toho.net.packets.Packet01Disconnect;
import hu.spykeh.toho.net.packets.Packet02Move;
import hu.spykeh.toho.net.packets.Packet.PacketTypes;
import hu.spykeh.toho.net.packets.Packet03Shoot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread {
	private InetAddress ip; // ide kapcsolódunk(szerver ip)
	private DatagramSocket socket;
	private Jatek jatek;

	public GameClient(Jatek jatek, String ip) {
		this.jatek = jatek;
		try {
			this.socket = new DatagramSocket();
			this.ip = InetAddress.getByName(ip);
		} catch (SocketException e) {

			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

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
			this.parsePacket(packet.getData(), packet.getAddress(),
					packet.getPort());
			// String message = new String(packet.getData());
			// System.out.println("SERVER > " + message);
		}
	}

	public void parsePacket(byte[] data, InetAddress ip, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPackets(message.substring(0, 2));
		Packet packet = null;
		switch (type) {
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			handleLogin((Packet00Login) packet, ip, port);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
					+ ((Packet01Disconnect) packet).getUserName()
					+ " has left the game...");
			jatek.level.stage.removePlayer(((Packet01Disconnect) packet)
					.getUserName());
			break;
		case MOVE:
			packet = new Packet02Move(data);
			movePlayer((Packet02Move) packet);
			break;
		case SHOOT:
			packet = new Packet03Shoot(data);
			shootHandler((Packet03Shoot) packet);
			break;
		default:
		}
	}

	public void handleLogin(Packet00Login packet, InetAddress ip, int port) {
		System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
				+ ((Packet00Login) packet).getUserName() + " has connected...");
		PlayerMP player = new PlayerMP(jatek.level.stage, packet.getUserName(),
				packet.getX(), packet.getY(), ip, port);
		jatek.level.stage.addPlayer(player);
	}

	public void movePlayer(Packet02Move packet) {

		jatek.level.stage.movePlayer(packet.getUserName(), packet.getX(),
				packet.getY());

	}

	private void shootHandler(Packet03Shoot packet) {
		System.out.println(packet.getUserName()+ " shooting");
		jatek.level.stage.setShooting(packet.getUserName(), packet.isShooting());
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, 1551);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
