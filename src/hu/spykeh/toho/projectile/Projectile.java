package hu.spykeh.toho.projectile;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class Projectile {
	private int x,y;
	public Sprite sprite;
	public boolean fired = true;
	public int speed = 5;
	public int dmg;
	public Projectile(Sprite sprite, int dmg){
		this.sprite = sprite;
		this.dmg = dmg;
	}
	
	public void render(int x, int y, Screen screen){
		
	}
	
	public void setPos(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x + (sprite.SIZE/2);
	}
	public int getY(){
		return y + (sprite.SIZE/2);
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
	
	public void move(){
		y -= speed;
	}
	public boolean isStoppable(){
		return false;
	}
}
