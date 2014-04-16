package hu.spykeh.toho.net;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.net.packets.Packet;
import hu.spykeh.toho.net.packets.Packet.PacketTypes;
import hu.spykeh.toho.net.packets.Packet00Login;
import hu.spykeh.toho.net.packets.Packet01Disconnect;
import hu.spykeh.toho.net.packets.Packet02Move;
import hu.spykeh.toho.net.packets.Packet03Shoot;

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
			handleLogin((Packet00Login)packet,ip,port);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
					+ ((Packet01Disconnect)packet).getUserName() + " has disconnected...");
			this.removeConnection(((Packet01Disconnect)packet));
			break;
		case MOVE:
			packet = new Packet02Move(data);
			moveHandler((Packet02Move)packet);
			break;
		case SHOOT:
			packet = new Packet03Shoot(data);
			shootHandler((Packet03Shoot)packet);
			break;
		default:
		}
	}

	public void handleLogin(Packet00Login packet, InetAddress ip, int port){
		System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
				+ packet.getUserName() + " has connected...");
		PlayerMP player = new PlayerMP(jatek.level.stage, packet.getUserName(), packet.getX(), packet.getY(), ip, port);
		this.addConnection(player, packet);
	}
	/*Megmozgatja az összes karaktert, kivéve a küldõt*/
	public void moveHandler(Packet02Move packet){
		if(getPlayerMP(packet.getUserName()) != null){
			int index = getPlayerMPIndex(packet.getUserName());
			connectedPlayers.get(index).setPos(packet.getX(), packet.getY());
		}
		packet.writeData(this);
	}
	
	public void shootHandler(Packet03Shoot packet){
		int index = getPlayerMPIndex(packet.getUserName());
		connectedPlayers.get(index).setShooting(true);
		packet.writeData(this);
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
				
				packet = new Packet00Login(p.getUsername(),p.getX(),p.getY()); //az online játékosok küldjék el adataikat.
				sendData(packet.getData(), player.ip, player.port);
			}

		}
		if(!alreadyConnected){
			this.connectedPlayers.add(player);
		}

	}

	public void removeConnection(Packet01Disconnect packet){
		this.connectedPlayers.remove(getPlayerMPIndex(packet.getUserName()));
		packet.writeData(this);
		
	}
	
	public PlayerMP getPlayerMP(String username){
		for(PlayerMP p : connectedPlayers){
			if(p.getUsername().equals(username)){
				return p;
			}
		}
		return null;
	}
	
	public int getPlayerMPIndex(String username){
		int index = 0;
		for(PlayerMP p : connectedPlayers){
			if(p.getUsername().equals(username)){
				break;
			}
			index++;
		}
		return index;
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
