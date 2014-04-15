package hu.spykeh.toho.entities;

import java.net.InetAddress;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.levels.Level;

public class PlayerMP extends Player{

	private String name;
	public InetAddress ip;
	public int port;
	public PlayerMP(Level level,String name, int x, int y, Keyboard input, InetAddress ip, int port) {
		super(level,name, x, y, input);
		this.name = name;
		this.ip = ip;
		this.port = port;
	}
	public PlayerMP(Level level,String name, int x, int y, InetAddress ip, int port) {
		super(level,name, x, y, null);
		this.ip = ip;
		this.port = port;
	}
	
	public void update(){
		super.update();
	}
}
