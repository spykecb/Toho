package hu.spykeh.toho.sprites;

import java.util.ArrayList;

import hu.spykeh.toho.gfx.SpriteSheet;

public class Sprite {

	private int x, y;
	private SpriteSheet sheet;
	public final int SIZE;
	public int[] pixels;
	
	public static Sprite player = new Sprite(0,4,32,SpriteSheet.sheet);
	public static Sprite stone = new Sprite(3,0,16,SpriteSheet.sheet);
	public static Sprite mob1 = new Sprite(3,4,32,SpriteSheet.sheet);
	public static Sprite projectile = new Sprite(0,1,16,SpriteSheet.sheet);

	public Sprite(int x, int y, int size, SpriteSheet sheet){
		this.x = x*size;
		this.y = y*size;
		this.SIZE = size;
		this.sheet = sheet;
		pixels = new int[SIZE*SIZE];
		load();
		
	}
	private void load(){
		for(int y = 0 ; y < SIZE ; y++){
			for(int x = 0 ; x < SIZE ; x++){
				pixels[x + y*SIZE] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.SIZEW];
			}
		}
	}
}
