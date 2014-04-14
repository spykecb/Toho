package hu.spykeh.toho.entities;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class Mob {
	private String name;
	private int xPos;
	private int yPos;
	Keyboard input;
	public Mob(String name, int x, int y){
		this.name = name;
		xPos = x;
		yPos = y;

	}
	public void render(Screen screen){
		screen.renderPlayer(xPos,yPos,Sprite.mob1);
	}
	
	public void move(){

	}
}
