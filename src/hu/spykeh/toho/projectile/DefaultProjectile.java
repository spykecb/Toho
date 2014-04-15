package hu.spykeh.toho.projectile;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class DefaultProjectile extends Projectile{

	public DefaultProjectile(Sprite sprite, int dmg){
		super(sprite,dmg);
	}
	
	public void render(int x, int y, Screen screen){
		if(fired)
			screen.renderProjectile(x,y,this);
	}
}
