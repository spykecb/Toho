package hu.spykeh.toho.entities;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class Mob {
	private String name;
	protected int xPos;
	protected int yPos;
	public int hitbox = 24;
	private int speed;
	private int health;
	public Mob(String name, int x, int y,Keyboard input){
		this.name = name;
		xPos = x;
		yPos = y;
		health = 10;
		input = null;

	}
	public void render(Screen screen){
		screen.renderPlayer(xPos,yPos,Sprite.mob1);
	}
	
	public void setPos(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	
	public void move(){

	}
}
