package hu.spykeh.toho.projectile;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class DefaultProjectile extends Projectile{

	public DefaultProjectile(Sprite sprite){
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderProjectile(x,y,this);
	}
}
