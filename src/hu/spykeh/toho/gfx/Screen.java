package hu.spykeh.toho.gfx;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;
import hu.spykeh.toho.tile.Tile;

import java.util.Random;

public class Screen {
	public int width, height;
	public int[] pixels;
	int tileIndex;
	public boolean flashlight = false;
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


	public void renderTile(int xp, int yp, Tile t, int scale){
		for(int x = 0 ; x < t.sprite.SIZE ; x++){
			int xa = xp + x; //abszolut pozicio
				for(int y = 0 ; y < t.sprite.SIZE ; y++){
					int ya = yp + y; //abszolut pozicio
					if(ya < 0 || xa < 0 || ya > height || xa > width) break;
					if(!flashlight){
						pixels[ya*width + xa] = t.sprite.pixels[y*t.sprite.SIZE + x];
					}else{
						int playerX = (int)Jatek.jatek.level.stage.player.getX()+Sprite.player.SIZE/2;
						int playerY = (int)Jatek.jatek.level.stage.player.getY()+Sprite.player.SIZE/2;
						double tav = Math.sqrt( (playerX-xa)*(playerX-xa) + (playerY-ya)*(playerY-ya) );
						if(tav < 50)
							pixels[ya*width + xa] = t.sprite.pixels[y*t.sprite.SIZE + x];
						else
							pixels[ya*width + xa] = 0x000000;
					}
						
				}
		}
	}
	
	public void renderFont(int xp, int yp, Sprite sprite,int color, int scale){
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
	
	public void renderPlayer(int xp, int yp, Mob mob, int scale, boolean hitbox){
		int scaleMap = scale - 1;
		for(int x = 0 ; x < mob.getSprite().SIZE ; x++){
			int xPixel = xp + x + (scaleMap*x) - ((scaleMap << 4) / 2);
				for(int y = 0 ; y < mob.getSprite().SIZE ; y++){
					int yPixel = yp + y+ (scaleMap*y) - ((scaleMap<< 3) / 2);

					for(int xScale = 0 ; xScale < scale ; xScale++){
						if(xScale+xPixel < 0 || xScale+xPixel >= width)
							break;
						for(int yScale = 0; yScale < scale ; yScale++){
							if(yScale+yPixel < 0 || yScale+yPixel >= height)
								break;
							int color = mob.getSprite().pixels[y*mob.getSprite().SIZE + x];
							if(color != 0xffff00ff)
								pixels[(yPixel+yScale)*width + (xPixel+xScale)] = color;
							if(hitbox){
								if(x == mob.hitboxFromX && y >= mob.hitboxFromY && y <= mob.hitboxToY){
									pixels[(yPixel+yScale)*width + (xPixel+xScale)] = 0xff0000;
								}else if(y == mob.hitboxFromY && x >= mob.hitboxFromX && x <= mob.hitboxToX){
									pixels[(yPixel+yScale)*width + (xPixel+xScale)] = 0xff0000;
								}else if(x == mob.hitboxToX && y >= mob.hitboxFromY && y <= mob.hitboxToY){
									pixels[(yPixel+yScale)*width + (xPixel+xScale)] = 0xff0000;
								}else if(y == mob.hitboxToY && x >= mob.hitboxFromX && x <= mob.hitboxToX){
									pixels[(yPixel+yScale)*width + (xPixel+xScale)] = 0xff0000;
								}

							}
							
						}
						
					}
					
				}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p, int scale, boolean hitbox){
		for(int x = 0 ; x < p.sprite.SIZE ; x++){
			int xa = xp + x; //abszolut pozicio
				for(int y = 0 ; y < p.sprite.SIZE ; y++){
					int ya = yp + y; //abszolut pozicio
					if(ya < 0 || xa < 0 || ya > height || xa > width) break;
					int color = p.sprite.pixels[y*p.sprite.SIZE + x];
					if(color != 0xffff00ff)
						pixels[ya*width + xa] = p.sprite.pixels[y*p.sprite.SIZE + x];
					if(hitbox){
						if(hitbox){
							if(x == p.hitboxFromX && y >= p.hitboxFromY && y <= p.hitboxToY){
								pixels[ya*width + xa] = 0xff0000;
							}else if(y == p.hitboxFromY && x >= p.hitboxFromX && x <= p.hitboxToX){
								pixels[ya*width + xa] = 0xff0000;
							}else if(x == p.hitboxToX && y >= p.hitboxFromY && y <= p.hitboxToY){
								pixels[ya*width + xa] = 0xff0000;
							}else if(y == p.hitboxToY && x >= p.hitboxFromX && x <= p.hitboxToX){
								pixels[ya*width + xa] = 0xff0000;
							}

						}
						
					}
				}
		}
	}
	
	public void renderImage(int xp, int yp, ImageLoader image){
		for(int x = 0; x < width ; x++){
			int xa = x + xp;
			for(int y = 0 ; y < height ; y++){
				int ya = y + yp;
				pixels[ya*width + xa] = image.pixels[y*image.SIZEW + x];
			}
		}
	}
	
}
