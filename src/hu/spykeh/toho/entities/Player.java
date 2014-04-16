package hu.spykeh.toho.entities;

import java.util.ArrayList;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.levels.Stage;
import hu.spykeh.toho.net.packets.Packet02Move;
import hu.spykeh.toho.net.packets.Packet03Shoot;
import hu.spykeh.toho.projectile.DefaultProjectile;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;


public class Player extends Mob{

	private String name;

	private int speed;
	private int fireSpeed;
	private int fireCounter = 0;
	private boolean shooting = false;
	Keyboard input;
	Screen screen;
	public Stage stage;
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public Player(Stage stage,String name, int x, int y, Keyboard input){
		super(name,x,y,input);
		this.stage = stage;
		this.name = name;
		fireSpeed = 10;
		speed = 2;
		this.input = input;

	}
	public void render(Screen screen){
		this.screen = screen;
		screen.renderPlayer(xPos,yPos,Sprite.player);
		for(int i = 0 ; i < projectiles.size() ; i++){
			projectiles.get(i).render(projectiles.get(i).getX(),projectiles.get(i).getY(), screen);
		}
		
	}
	
	public void update(){
		if(input != null){
			inputHandler();
			shoot(new DefaultProjectile(Sprite.projectile,30));
			for(int i = 0 ; i < projectiles.size() ; i++){
				projectiles.get(i).move();
				if(projectiles.get(i).getY() < Stage.y)projectiles.remove(i);
				
			}
		}else{
			shoot(new DefaultProjectile(Sprite.projectile,30));
			for(int i = 0 ; i < projectiles.size() ; i++){
				projectiles.get(i).move();
				if(projectiles.get(i).getY() < Stage.y)projectiles.remove(i);
			}
		}
	}
	
	public void inputHandler(){
		if(input.up | input.down | input.left | input.right){
			move();
		}
		if(input.y){
			if(shooting == false){
				setShooting(true);
				/*igy talán tul sok csomagot küldünk*/
				Packet03Shoot packet = new Packet03Shoot(name,true);
				packet.writeData(Jatek.jatek.client);
			}
		}else{
			if(shooting == true){
				setShooting(false);
				Packet03Shoot packet = new Packet03Shoot(name,false);
				packet.writeData(Jatek.jatek.client);
			}
		}
		if(input.esc){
			quit();
		}
	}
	public void move(){
		
		if(input.up && yPos-speed+2 > Stage.y){
			yPos = yPos - speed;
		}
		if(input.down && yPos+speed+16 < Stage.y+Stage.sizeY){
			yPos = yPos + speed;
		}
		if(input.left && xPos-speed+7 > Stage.x){
			xPos = xPos - speed;
		}
		if(input.right && xPos+speed+20 < Stage.x+Stage.sizeX){
			xPos = xPos + speed;
		}
		Packet02Move packet = new Packet02Move(getUsername(), xPos, yPos);
		packet.writeData(Jatek.jatek.client);

		
	}

	public void shoot(Projectile p){
		if(isShooting()){
			fireCounter++;
			if(fireCounter % fireSpeed == 0){
				projectiles.add(p);
				projectiles.get(projectiles.size()-1).setPos(xPos, yPos);
				System.out.println("Boom!" + projectiles.size());
			}
		}
		
	}
	public void quit(){
		
		Jatek.state = 0;
		
	}
	public String getUsername(){
		return this.name;
	}
	public boolean isShooting() {
		return shooting;
	}
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
}
