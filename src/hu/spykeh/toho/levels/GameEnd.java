package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;

public class GameEnd extends Menu{
	public String winner = "";
	public String wonString = "won";
	private String menu = "press ENTER to continue";
	public GameEnd(Keyboard input, String winner){
		super(input);
		this.winner = winner;
		chosen = 0;
	}

	public void render(Screen screen) {
		renderBG(screen);
		renderString(screen, wonString, menu);

	}
	public void renderString(Screen screen, String string,String value) {
		int color = 0xffffff;
		Font.render(winner, screen.width / 2 - 30, screen.height / 2 , screen,color);
		Font.render(string, screen.width / 2 - 22 + winner.length()*8 , screen.height / 2 , screen,color);
		Font.render(value, screen.width / 2 - 50, screen.height / 2 + 20 , screen,0xffffff);
		
	}
	public void update() {
		chosen = 0;

	}
}
