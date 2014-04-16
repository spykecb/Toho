package hu.spykeh.toho.entities;

import java.net.InetAddress;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.levels.Stage;

public class PlayerMP extends Player{

	public InetAddress ip;
	public int port;
	public boolean online = true;
	public PlayerMP(Stage stage,String name, int x, int y, Keyboard input, InetAddress ip, int port) {
		super(stage,name, x, y, input);
		this.ip = ip;
		this.port = port;
	}
	public PlayerMP(Stage stage,String name, int x, int y, InetAddress ip, int port) {
		super(stage,name, x, y, null);
		this.ip = ip;
		this.port = port;
	}
	
	public void update(){
		super.update();
	}
	public void quit(){
		if(input.esc){
			Jatek.state = 0;
			online = false;
			System.out.println("quitMP");
		}
	}
}
