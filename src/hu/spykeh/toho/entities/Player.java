package hu.spykeh.toho.entities;

import java.util.ArrayList;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.levels.Level;
import hu.spykeh.toho.levels.Stage;
import hu.spykeh.toho.projectile.DefaultProjectile;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;


public class Player extends Mob{

	private String name;

	private int speed;
	private int fireSpeed;
	private int fireCounter = 0;
	Keyboard input;
	Screen screen;
	public Level level;
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public Player(Level level,String name, int x, int y, Keyboard input){
		super(name,x,y,input);
		this.level = level;
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
			move();
			shoot(new DefaultProjectile(Sprite.projectile,30));
			for(int i = 0 ; i < projectiles.size() ; i++){
				projectiles.get(i).move();
				System.out.println(i + ". projectile x: " + projectiles.get(i).getX() + ", y : " + projectiles.get(i).getY());
				if(projectiles.get(i).getY() < Stage.y)projectiles.remove(i);
				
			}
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
		
	}

	public void shoot(Projectile p){
		if(input.y){
			fireCounter++;
			if(fireCounter % fireSpeed == 0){
				projectiles.add(p);
				projectiles.get(projectiles.size()-1).setPos(xPos, yPos);
				System.out.println("Boom!" + projectiles.size());
			}
		}
	}
	
	public String getUsername(){
		return this.name;
	}
}
