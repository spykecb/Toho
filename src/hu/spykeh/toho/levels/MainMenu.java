package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;

public class MainMenu extends Menu {
	private String[] menu = { "Start", "Options", "Quit" };
	private String[] menuValue = { "", "", "" };

	public MainMenu(Keyboard input) {
		super(input);
	}

	public void render(Screen screen) {
		renderBG(screen);
		renderStrings(screen, this.menu, this.menuValue);
	}

	public void update() {

		if (Menu.chosen > menu.length - 1)
			Menu.chosen = 0;
		if (Menu.chosen < 0)
			Menu.chosen = menu.length - 1;

	}
}
