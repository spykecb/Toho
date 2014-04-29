package hu.spykeh.toho.entities;

import java.util.Random;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.levels.Stage;
import hu.spykeh.toho.sprites.Sprite;

public class PlayerBot extends Player{

	private int speed;
	Random r = new Random();
	private boolean up,down,left,right;
	private int side;
	public PlayerBot(Stage stage, String name,Sprite sprite, int x, int y, Keyboard input, int side) {
		super(stage, name,sprite, x, y, null,side);
		this.side = side;
		setShooting(false );
		speed = 2;
		setHp(10);
		
	}
	
	public void moveBot(){
		switch(r.nextInt(4)){
		case 0:
			up = true;
			down = false;
			left = false;
			right = false;
			break;
		case 1:
			up = false;
			down = true;
			left = false;
			right = false;
			break;
		case 2:
			up = false;
			down = false;
			left = false;
			right = true;
			break;
		case 3:
			up = false;
			down = false;
			left = true;
			right = false;
			break;
		default:
			break;
		}
		if (up && yPos - speed  > stage.getStageOne(0).y) {
			yPos = yPos - speed;
		}
		if (down && yPos + speed < stage.getStageOne(0).y + stage.getStageOne(0).sizeY) {
			yPos = yPos + speed;
		}
		if (left && xPos - speed > stage.getStageOne(0).x) {
			xPos = xPos - speed;
		}
		if (right && xPos + speed < stage.getStageOne(0).x + stage.getStageOne(0).sizeX) {
			xPos = xPos + speed;
		}
	}

}
