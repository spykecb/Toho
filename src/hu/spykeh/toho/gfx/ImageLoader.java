package hu.spykeh.toho.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	private String path;
	public final int SIZEW, SIZEH;
	public int[] pixels;
	
	public static ImageLoader bg = new ImageLoader("/bg.jpg",400,250);
	
	public ImageLoader(String path, int size){
		this.path = path;
		SIZEW = size;
		SIZEH = size;
		pixels = new int[SIZEW * SIZEH];
		load();
	}
	
	public ImageLoader(String path, int sizeW, int sizeH){
		this.path = path;
		SIZEW = sizeW;
		SIZEH = sizeH;
		pixels = new int[SIZEW * SIZEH];
		load();
	}
	
	private void load(){
		try {
			BufferedImage image = ImageIO.read(ImageLoader.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
}
