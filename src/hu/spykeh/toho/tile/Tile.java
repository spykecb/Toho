package hu.spykeh.toho.tile;

import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.sprites.Sprite;

public class Tile {
	public int x,y;
	public Sprite sprite;
	
	public static Tile stone = new StoneTile(Sprite.stone);
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
		
	}
	
	public boolean isSolid(){
		return false;
	}
	

}
