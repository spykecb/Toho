package hu.spykeh.toho.net;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.levels.StageMP;
import hu.spykeh.toho.net.packets.Packet;
import hu.spykeh.toho.net.packets.Packet00Login;
import hu.spykeh.toho.net.packets.Packet01Disconnect;
import hu.spykeh.toho.net.packets.Packet02Move;
import hu.spykeh.toho.net.packets.Packet06ReadyToStart;
import hu.spykeh.toho.net.packets.Packet.PacketTypes;
import hu.spykeh.toho.net.packets.Packet03Shoot;
import hu.spykeh.toho.net.packets.Packet04MobSpawn;
import hu.spykeh.toho.net.packets.Packet05Ready;
import hu.spykeh.toho.sprites.Sprite;

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
		setName("Client Thread");
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
			this.parsePacket(packet.getData(), packet.getAddress(),packet.getPort());
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
			jatek.level.stageMP.removePlayer(((Packet01Disconnect) packet)
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
		case MOBSPAWN:
			packet = new Packet04MobSpawn(data);
			spawnMob((Packet04MobSpawn)packet);
			break;
		case READY:
			packet = new Packet05Ready(data);
			readyHandler((Packet05Ready)packet);
		case READYTOSTART:
			packet = new Packet06ReadyToStart(data);
			startHandler((Packet06ReadyToStart)packet);
		default:
		}
	}

	private void startHandler(Packet06ReadyToStart packet) {
		int index = StageMP.stage.getPlayerMPIndex(packet.getUserName());
		StageMP.stage.players.get(index).setLoaded(true);
		System.out.println(packet.getUserName() + " set to READY");
	}

	private void readyHandler(Packet05Ready packet) {
		int index = StageMP.stage.getPlayerMPIndex(packet.getUserName());
		StageMP.stage.players.get(index).setReady(true);
		
	}

	private void spawnMob(Packet04MobSpawn packet) {
		System.out.println("stageMP : " + StageMP.stage);
		System.out.println("stageOne : " + StageMP.stage.getStageOne(packet.getSide()));
			StageMP.stage.getStageOne(packet.getSide()).mobs.add(new Mob(StageMP.stage,packet.getName(),Sprite.mob1,packet.getX(),packet.getY(),packet.getSide()));
			System.out.println("spawned:" + packet.getSide() + "    "+ packet.getX());
			StageMP.stage.getStageOne(packet.getSide()).sentSpawnRequest = false;
	}

	public void handleLogin(Packet00Login packet, InetAddress ip, int port) {
		System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
				+ ((Packet00Login) packet).getUserName() + " has joined the game...");
		PlayerMP player = new PlayerMP(StageMP.stage, packet.getUserName(),Sprite.player, packet.getX(), packet.getY(),packet.getSide(), ip, port);
		jatek.level.stageMP.addPlayer(player);
		System.out.println("multi játékos hozzáadva");
	}

	public void movePlayer(Packet02Move packet) {
		jatek.level.stageMP.movePlayer(packet.getUserName(), packet.getX(),
				packet.getY());

	}

	private void shootHandler(Packet03Shoot packet) {
		jatek.level.stageMP.setShooting(packet.getUserName(), packet.isShooting());
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
