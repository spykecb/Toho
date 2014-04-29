package hu.spykeh.toho.entities;

import java.net.InetAddress;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.levels.StageMP;
import hu.spykeh.toho.sprites.Sprite;

public class PlayerMP extends Player{

	public InetAddress ip;
	public int port;
	public boolean online = true;
	private boolean ready = false;
	private boolean loaded = false;
	public PlayerMP(StageMP stage,String name,Sprite sprite, int x, int y, Keyboard input, int side, InetAddress ip, int port) {
		super(stage,name,sprite, x, y, input,side);
		this.stage = stage;
		this.ip = ip;
		this.port = port;
	}
	public PlayerMP(StageMP stage,String name,Sprite sprite, int x, int y,int side,  InetAddress ip, int port) {
		super(stage,name,sprite, x, y, null,side);
		this.stage = stage;
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
	
	public boolean isReady(){
		return ready;
	}
	
	public void setReady(boolean ready){
		this.ready = ready;
	}
	public boolean isLoaded(){
		return loaded;
	}
	
	public void setLoaded(boolean loaded){
		this.loaded = loaded;
	}
	
}
