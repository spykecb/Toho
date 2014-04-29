package hu.spykeh.toho.projectile;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class Projectile {
	private double x;
	private double y;
	public Sprite sprite;
	public int hitboxFromX;
	public int hitboxFromY;
	public int hitboxToX;
	public int hitboxToY;
	public boolean fired = true;
	private boolean hitbox = false;
	public int dmg;
	private int index;
	public Projectile(Sprite sprite, int dmg){
		this.sprite = sprite;
		this.dmg = dmg;
		setHitbox();
	}
	public void setHitbox(){
		if(sprite == Sprite.projectile1){
			hitboxFromX = 6;
			hitboxFromY = 5;
			hitboxToX = 8;
			hitboxToY = 12;
		}else if(sprite == Sprite.projectile2){
			hitboxFromX = 4;
			hitboxFromY = 4;
			hitboxToX = 11;
			hitboxToY = 11;
		}
			
	}
	public void render(int x, int y, Screen screen){
		screen.renderProjectile(x, y, this, 1, hitbox);
	}
	
	public void setPos(double x, double y){
		this.x =  x;
		this.y =  y;
	}
	
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	
	public void update(){
		if(y < 0)
			remove();
	}
	public void fire(){
		fired = true;
	}
	public void remove(){
		fired = false;
	}
	
	public boolean isStoppable(){
		return false;
	}
	
	public Sprite getSprite(){
		return this.sprite;
	}
	
	public int getDmg(){
		return this.dmg;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
}
