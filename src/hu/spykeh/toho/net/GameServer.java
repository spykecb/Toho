package hu.spykeh.toho.net;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.levels.StageMP;
import hu.spykeh.toho.net.packets.Packet;
import hu.spykeh.toho.net.packets.Packet.PacketTypes;
import hu.spykeh.toho.net.packets.Packet00Login;
import hu.spykeh.toho.net.packets.Packet01Disconnect;
import hu.spykeh.toho.net.packets.Packet02Move;
import hu.spykeh.toho.net.packets.Packet03Shoot;
import hu.spykeh.toho.net.packets.Packet04MobSpawn;
import hu.spykeh.toho.net.packets.Packet05Ready;
import hu.spykeh.toho.net.packets.Packet06ReadyToStart;
import hu.spykeh.toho.sprites.Sprite;

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
	private int readyDb = 0;
	private List<Mob> mobs1 = new ArrayList<Mob>();
	private List<Mob> mobs2 = new ArrayList<Mob>();
	public GameServer(Jatek jatek) {
		setName("Server Thread");
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
		case MOBSPAWN:
			packet = new Packet04MobSpawn(data);
			spawnMobs((Packet04MobSpawn)packet);
			break;
		case READY:
			packet = new Packet05Ready(data);
			readyHandler((Packet05Ready)packet, ip, port);
			break;
		case READYTOSTART:
			packet = new Packet06ReadyToStart(data);
			startHandler((Packet06ReadyToStart)packet, ip, port);
			break;
		default:
		}
	}

	private void startHandler(Packet06ReadyToStart packet, InetAddress ip,int port) {
		for(PlayerMP p : connectedPlayers){
			if(p.getName().equals(packet.getUserName())){
				p.setLoaded(true);
			}else{
				sendData(packet.getData(),p.ip,p.port);
				
				if(p.isLoaded()){
					packet = new Packet06ReadyToStart(p.getName()); //az online játékosok küldjék el a státuszukat.
					sendData(packet.getData(), ip, port);
				}
				
			}
		}
		
	}

	private void readyHandler(Packet05Ready packet, InetAddress ip, int port) {
		for(PlayerMP p : connectedPlayers){
			if(p.getName().equals(packet.getUserName())){
				p.setReady(true);
			}else{
				sendData(packet.getData(),p.ip,p.port);
				
				if(p.isReady()){
					packet = new Packet05Ready(p.getName()); //az online játékosok küldjék el a státuszukat.
					sendData(packet.getData(), ip, port);
				}
				
			}
		}
	}

	private void spawnMobs(Packet04MobSpawn packet) {
		if(packet.getSide() == 0){
			mobs1.add(new Mob(StageMP.stage,packet.getName(),Sprite.mob1,packet.getX(),packet.getY(),0));
		}else{
			mobs2.add(new Mob(StageMP.stage,packet.getName(),Sprite.mob1,packet.getX(),packet.getY(),1));
		}
		
		packet.writeData(this);
	}

	public void handleLogin(Packet00Login packet, InetAddress ip, int port){
		if(connectedPlayers.size() <= 2){
			System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
					+ packet.getUserName() + " has connected...");
			PlayerMP player = new PlayerMP(StageMP.stage, packet.getUserName(),Sprite.player, packet.getX(), packet.getY(),packet.getSide(), ip, port);
			this.addConnection(player, packet);
		}else{
			System.out.println("[" + ip.getHostAddress() + ":" + port + "]"
					+ packet.getUserName() + " started spectating...");
			PlayerMP player = new PlayerMP(StageMP.stage, packet.getUserName(),Sprite.player, packet.getX(), packet.getY(),2, ip, port);
			this.addConnection(player, packet);
		}
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
			if (player.getName().equalsIgnoreCase(p.getName())) { 
				if (p.ip == null) {
					p.ip = player.ip;
				}
				if (p.port == -1) {
					p.port = player.port;
				}
				alreadyConnected = true;
			} else { 
				sendData(packet.getData(), p.ip, p.port); //küldjük el az online játékosoknak, hogy belépett
				
				packet = new Packet00Login(p.getName(),(int)p.getX(),(int)p.getY(), p.getSide()); //az online játékosok küldjék el adataikat.
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
			if(p.getName().equals(username)){
				return p;
			}
		}
		return null;
	}
	
	public int getPlayerMPIndex(String username){
		int index = 0;
		for(PlayerMP p : connectedPlayers){
			if(p.getName().equals(username)){
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
