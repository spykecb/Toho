package hu.spykeh.animations.projAnimations;

import java.util.ArrayList;
import java.util.List;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.projectile.Projectile;

public class BeamShot extends ProjectileAnimation{

	private int speed = 1;
	private double angle;
	double mod = Math.PI/6;
	public BeamShot(Projectile p, int x, int y, double angle){
		super(p,x,y,angle);
		this.angle = angle;
		for(int i = 0 ; i < 24 ; i++){
			this.proj.add(new Projectile(p.getSprite(), p.getDmg()));
			this.proj.get(i).setIndex(i);
			this.proj.get(i).setPos(x, y);
		}
	}
	
	public void move(){
		double mod;
		for(Projectile p : getProjectiles()){
			mod = p.getIndex()*(Math.PI/(4*3)) + angle;
			double modX = Math.sin(mod)*speed;
			double modY = Math.cos(mod)*speed;
			p.setPos(modX + p.getX(),modY + p.getY());
		}

	}
 
}
