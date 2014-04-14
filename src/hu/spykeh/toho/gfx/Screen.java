package hu.spykeh.toho.gfx;

import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;
import hu.spykeh.toho.tile.Tile;

import java.util.Random;

public class Screen {
	public int width, height;
	public int[] pixels;
	int tileIndex;
	Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}


	public void renderTile(int xp, int yp, Tile t){
		for(int x = 0 ; x < t.sprite.SIZE ; x++){
			int xa = xp + x; //abszolut pozicio
				for(int y = 0 ; y < t.sprite.SIZE ; y++){
					int ya = yp + y; //abszolut pozicio
					if(ya < 0 || xa < 0 || ya > height || xa > width) break;
					pixels[ya*width + xa] = t.sprite.pixels[y*t.sprite.SIZE + x];
				}
		}
	}
	
	public void renderFont(int xp, int yp, Sprite sprite,int color){
		for(int x = 0 ; x < sprite.SIZE ; x++){
			int xa = xp + x; //abszolut pozicio
				for(int y = 0 ; y < sprite.SIZE ; y++){
					int ya = yp + y; //abszolut pozicio
					if(ya < 0 || xa < 0 || ya > height || xa > width) break;
					int colori = sprite.pixels[y*sprite.SIZE + x];
					if(colori != 0xffff00ff)
						pixels[ya*width + xa] = color;
				}
		}
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite){
		for(int x = 0 ; x < sprite.SIZE ; x++){
			int xa = xp + x; //abszolut pozicio
				for(int y = 0 ; y < sprite.SIZE ; y++){
					int ya = yp + y; //abszolut pozicio
					if(ya < 0 || xa < 0 || ya > height || xa > width) break;
					int color = sprite.pixels[y*sprite.SIZE + x];
					if(color != 0xffff00ff)
						pixels[ya*width + xa] = color;
				}
		}
	}

	public void renderProjectile(int xp, int yp, Projectile p){
		
	}
	
}
