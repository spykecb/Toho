package hu.spykeh.toho.gfx;

import java.util.ArrayList;

import hu.spykeh.toho.sprites.Sprite;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ    0123456789.,:";
	public Sprite sprite;
	public static ArrayList<Sprite> font = new ArrayList<Sprite>();
	public static Font fonta = new Font(SpriteSheet.fontsheet);

	public Font(SpriteSheet sheet) {
		for (int i = 0; i < chars.length(); i++) {
			font.add(new Sprite(i % 30, i / 30 + 2, 8, sheet));
		}
	}

	public static void render(String msg, int x, int y, Screen screen, int color) {
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			screen.renderFont(x + i*8, y, font.get(chars.indexOf(msg.charAt(i))), color);
		}

	}


}
