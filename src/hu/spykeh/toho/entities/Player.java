package hu.spykeh.toho.entities;

import java.util.ArrayList;
import java.util.Iterator;

import hu.spykeh.animations.projAnimations.FiveShot;
import hu.spykeh.animations.projAnimations.ProjectileAnimation;
import hu.spykeh.animations.projAnimations.BeamShot;
import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.levels.Stage;
import hu.spykeh.toho.levels.StageMP;
import hu.spykeh.toho.net.packets.Packet02Move;
import hu.spykeh.toho.net.packets.Packet03Shoot;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;

public class Player extends Mob {

	private String name;
	private int scale = 1;
	private int side;
	private int fireRate;
	private int fireCounter = 0;
	private boolean hitbox = false;
	public Keyboard input;
	Screen screen;
	public ArrayList<ProjectileAnimation> projanim = new ArrayList<ProjectileAnimation>();
	public Stage stage;
	public Player(Stage stage, String name,Sprite sprite, int x, int y, Keyboard input, int side) {
		super(stage,name,sprite, x, y,side);
		this.side = side;
		
		if(name == null){
			this.name = Jatek.jatek.jatekosNev;
		}else{
			this.name = name;
		}
		
		this.stage = stage;
		setFireRate(30);
		setHp(10);
		setHitbox();
		setSpeed(2);
		this.input = input;
		
		if(input == null){
			xPos += stage.getDiff();
			
		}

	}
	public void setHitbox() {
		this.hitboxFromX = 5;
		this.hitboxFromY = 3;
		this.hitboxToX = 10;
		this.hitboxToY = 12;
	}
	

	public void update() {
		if (input != null) {
			inputHandler();
		}else{
			//moveBot();
		}
		shootHandler();
		
	}
	

	public void moveBot() {
		
	}

	public void inputHandler() {
		if (input.up | input.down | input.left | input.right) {
			move();
		}
		if(input.shift){
			setSpeed(1);
		}else if(!input.shift){
			setSpeed(2);
		}
		if (input.y) {
			if (!isShooting()) {
				setShooting(true);
				/* igy talán tul sok csomagot küldünk */
				if(Jatek.jatek.client != null){
					Packet03Shoot packet = new Packet03Shoot(name, true);
					packet.writeData(Jatek.jatek.client);
				}
			}
		} else {
			if (isShooting()) {
				setShooting(false);
				if(Jatek.jatek.client != null){
					Packet03Shoot packet = new Packet03Shoot(name, false);
					packet.writeData(Jatek.jatek.client);
				}
			}
		}
		if (input.esc) {
			quit();
		}
	}

	public void move() {
		double xa = 0;
		double ya = 0;
		if (input.up)ya -= getSpeed();
		if (input.down)ya += getSpeed();
		if (input.left)xa -= getSpeed();
		if (input.right)xa += getSpeed();
		if(ya != 0 && xa != 0){//2 gomb
			ya = Math.sin(Math.PI/4)*ya;
			xa = Math.sin(Math.PI/4)*xa;
		}
		if (input.up && yPos + ya > stage.getStageOne(0).y) {
			yPos = yPos + ya;
		}

		if (input.down && yPos + hitboxToY + ya < stage.getStageOne(0).y + stage.getStageOne(0).sizeY) {
			yPos = yPos + ya;
		}
		if (input.left && xPos + xa > stage.getStageOne(0).x) {
			xPos = xPos + xa;
		}
		if (input.right	&& xPos + hitboxToX + xa < stage.getStageOne(0).x + stage.getStageOne(0).sizeX) {
			xPos = xPos + xa;
		}
		if(Jatek.jatek.client != null){
			Packet02Move packet = new Packet02Move(getName(), (int)xPos, (int)yPos);
			packet.writeData(Jatek.jatek.client);
		}

	}

	public void shoot(double angle) {
		getProjAnim().add(new FiveShot(new Projectile(Sprite.projectile1,100),(int)xPos,(int)yPos,angle));
	}

	public void quit() {
		Jatek.state = 0;
	}



}
