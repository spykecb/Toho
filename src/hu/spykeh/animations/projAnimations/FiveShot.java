package hu.spykeh.animations.projAnimations;

import java.util.ArrayList;
import java.util.List;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;
/*	5 db felfelé menõ projectile, egymás mellett.
 *  Ebbõl 3 követi a legközelebbi mobot
 * */
public class FiveShot extends ProjectileAnimation{
	private int mod = -3;
	private int x,y;
	private int speed = 3;
	private double angle;
	public FiveShot(Projectile p, int x , int y, double angle){
		super(p,x,y,angle);
		this.angle = angle;
		for(int i = 0 ; i < 5 ; i++){
			this.proj.add(new Projectile(p.getSprite(), p.getDmg()));
			this.proj.get(i).setIndex(i);
			this.proj.get(i).setPos(x+mod, y);
			mod += 5;
		}
		
	}

	
	public void move(){
		for(Projectile p : getProjectiles()){
			double modX = Math.sin(angle)*speed;
			double modY = Math.cos(angle)*speed;
			p.setPos(modX + p.getX(),modY + p.getY());
		}
	}
	
	
}
