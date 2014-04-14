package hu.spykeh.toho.entities;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.gfx.SpriteSheet;
import hu.spykeh.toho.sprites.Sprite;
import hu.spykeh.toho.tile.Tile;


public class Player {

	private String name;
	private int xPos;
	private int yPos;
	private int speed;
	Keyboard input;
	public Player(String name, int x, int y, Keyboard input){
		this.name = name;
		xPos = x;
		yPos = y;
		speed = 2;
		this.input = input;

	}
	public void render(Screen screen){
		screen.renderPlayer(xPos,yPos,Sprite.player);
	}
	
	public void update(){
		move();
		shoot();
	}
	public void move(){

		if(input.up)yPos = yPos - speed;
		if(input.down)yPos = yPos + speed;
		if(input.left)xPos = xPos - speed;
		if(input.right)xPos = xPos + speed;
	}
	
	public void shoot(){
		if(input.y){
			System.out.println("Boom!");
		}
	}
}
