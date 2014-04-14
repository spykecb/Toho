package hu.spykeh.toho.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZEW, SIZEH;
	public int[] pixels;
	
	public static SpriteSheet sheet = new SpriteSheet("/spritesheet.png",256);
	public static SpriteSheet fontsheet = new SpriteSheet("/fontsheet.png",240,32);
	
	public SpriteSheet(String path, int size){
		this.path = path;
		SIZEW = size;
		SIZEH = size;
		pixels = new int[SIZEW * SIZEH];
		load();
	}
	
	public SpriteSheet(String path, int sizeW, int sizeH){
		this.path = path;
		SIZEW = sizeW;
		SIZEH = sizeH;
		pixels = new int[SIZEW * SIZEH];
		load();
	}
	
	private void load(){
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
}
