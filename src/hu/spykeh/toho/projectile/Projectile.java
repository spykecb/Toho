package hu.spykeh.toho.projectile;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class Projectile {
	public int x,y;
	public Sprite sprite;
	
	public Projectile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
		
	}
	
	public boolean isStoppable(){
		return false;
	}
}
