package hu.spykeh.animations.projAnimations;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class ProjectileAnimation {
	protected List<Projectile> proj = new ArrayList<Projectile>();
	private int x,y;
	private int speed = 1;
	private double angle;
	public ProjectileAnimation(Projectile p, int x, int y, double angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	

	public void move(){
		
	}
	
	
	public List<Projectile> getProjectiles(){
		return this.proj;
	}
}
