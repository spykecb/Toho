package hu.spykeh.toho.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.spykeh.animations.projAnimations.BeamShot;
import hu.spykeh.animations.projAnimations.ProjectileAnimation;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.levels.Stage;
import hu.spykeh.toho.levels.StageMP;
import hu.spykeh.toho.levels.StageOne;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;

public class Mob {
	private String name;
	protected double xPos;
	protected double yPos;
	private int fireRate;
	private int fireCounter = 0;
	public int hitboxFromX;
	public int hitboxFromY;
	public int hitboxToX;
	public int hitboxToY;
	private int speed;
	private int health;
	private int side;
	private Sprite sprite;
	private boolean shooting = true;
	private boolean hitbox = false;
	private double shootingAngle = Math.PI;
	public ArrayList<ProjectileAnimation> projanim = new ArrayList<ProjectileAnimation>();
	public Stage stage;
	public Mob(Stage stage, String name,Sprite sprite, int x, int y, int side){
		this.name = name;
		this.stage = stage;
		this.side = side;
		this.sprite = sprite;
		xPos = x;
		yPos = y;
		setHp(5);
		setFireRate(55);
		setHitbox();
		setSpeed(0);

	}
	public void setHitbox() {
		this.hitboxFromX = 0;
		this.hitboxFromY = 0;
		this.hitboxToX = 15;
		this.hitboxToY = 15;
	}
	
	public void render(Screen screen){
		if(getSide() == 0){

			screen.renderPlayer((int)xPos, (int)yPos, this,1, hitbox);
			//Font.render(String.valueOf((int)xPos) + ",", (int)xPos - 10, (int)yPos - 8, screen, 0xffffff);
			//Font.render(String.valueOf((int)yPos), (int)xPos+20, (int)yPos - 8, screen, 0xffffff);
		}else{
			screen.renderPlayer((int)xPos+stage.getDiff(), (int)yPos, this,1, hitbox);
			//Font.render(String.valueOf((int)xPos) + ",", (int)xPos - 10 + stage.getDiff(), (int)yPos - 8, screen, 0xffffff);
			//Font.render(String.valueOf((int)yPos), (int)xPos +20 + stage.getDiff(), (int)yPos - 8, screen, 0xffffff);
			
		}
		
		Iterator<ProjectileAnimation> it1 = getProjAnim().iterator();
		while(it1.hasNext()){
			ProjectileAnimation projanim = it1.next();
			Iterator<Projectile> it2 = projanim.getProjectiles().iterator();
			while(it2.hasNext()){
				Projectile p = it2.next();
				if(side == 0){
					p.render((int)p.getX(),(int)p.getY(), screen);
				}else{
					p.render((int)p.getX()+stage.getDiff(),(int)p.getY(), screen);
				}
			}
		}
	}
	
	public void update(){
		shootHandler();
	}
	public void setPos(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public double getX(){
		return xPos;
	}
	public double getY(){
		return yPos;
	}
	
	public String getName(){
		return name;
	}
	
	public void shoot(double angle){
		getProjAnim().add(new BeamShot(new Projectile(Sprite.projectile2,100),(int)xPos,(int)yPos,angle));

	}
	
	public void shootHandler(){
		if (isShooting()) {
			fireCounter++;
			if (fireCounter % getFireRate() == 0) {
				shootingAngle += Math.PI/90;
				shoot(shootingAngle);
			}
		}

		Iterator<ProjectileAnimation> it1 = getProjAnim().iterator();
		while(it1.hasNext()){
			ProjectileAnimation projanim = it1.next();
			projanim.move();
			if(projanim.getProjectiles().size() == 0){
				it1.remove();
			} 
			Iterator<Projectile> it2 = projanim.getProjectiles().iterator();
			while(it2.hasNext()){
				Projectile p = it2.next();
				if(getX() < stage.getStageOne(0).x + stage.getStageOne(0).sizeX){
					if (p.getY() < stage.getStageOne(0).y){
						it2.remove();
					}else if(p.getX() < stage.getStageOne(0).x){
						it2.remove();
					}else if(p.getX() > stage.getStageOne(0).x + stage.getStageOne(0).sizeX){
						it2.remove();
					}else if(p.getY() > stage.getStageOne(0).y + stage.getStageOne(0).sizeY){
						it2.remove();
					}
				}else{
					if (p.getY() < stage.getStageOne(1).y){
						it2.remove();
					}else if(p.getX() < stage.getStageOne(1).x){
						it2.remove();
					}else if(p.getX() > stage.getStageOne(1).x + stage.getStageOne(1).sizeX){
						it2.remove();
					}else if(p.getY() > stage.getStageOne(1).y + stage.getStageOne(1).sizeY){
						it2.remove();
					}
				}
				
			}

			
		} 
	}
	public void move(){

	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getSpeed(){
		return speed;
	}
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public boolean isShooting(){
		return this.shooting;
	}
	
	public double getShootAngle(){
		return this.shootingAngle;
	}
	
	public void setShootingAngle(double angle){
		this.shootingAngle = angle;
	} 
	
	public void setFireRate(int fireRate){
		this.fireRate = fireRate;
	}
	
	public int getFireRate(){
		return this.fireRate;
	}
	
	public ArrayList<ProjectileAnimation> getProjAnim(){
		return this.projanim;
	}
	
	public void setHp(int health){
		this.health = health;
	}
	public int getHp(){
		return this.health;
	}
	
	public Sprite getSprite(){
		return this.sprite;
	}
	
	public int getSide(){
		return side;
	}
}
