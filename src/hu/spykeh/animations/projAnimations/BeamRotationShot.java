package hu.spykeh.animations.projAnimations;

import hu.spykeh.toho.projectile.Projectile;

public class BeamRotationShot extends ProjectileAnimation {
	private int speed = 1;
	public double angle;
	public BeamRotationShot(Projectile p, int x, int y, double angle){
		super(p,x,y,angle);
		this.angle = angle;
		for(int i = 0 ; i < 8 ; i++){
			this.proj.add(new Projectile(p.getSprite(), p.getDmg()));
			this.proj.get(i).setIndex(i);
			this.proj.get(i).setPos(x, y);
		}
	}
	
	
	public void move(){
		double mod;
		for(Projectile p : getProjectiles()){
			mod = p.getIndex()*(Math.PI/4) + this.angle;
			double modX = Math.sin(mod)*speed;
			double modY = Math.cos(mod)*speed;
			int modXI = (int)Math.round(modX);
			int modYI = (int)Math.round(modY);
			p.setPos(modXI + p.getX(),modYI + p.getY());
		}


	}
}
