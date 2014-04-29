package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;

public class StartMenu extends Menu {

	private String[] menu = { "Start offline game", "Start Online Server",
			"Connect to Server" };
	private String[] menuValue = { "", "", "" };

	public StartMenu(Keyboard input) {
		super(input);
		chosen = 0;
	}

	public void render(Screen screen) {
		renderBG(screen);
		renderStrings(screen, menu, menuValue);

	}

	public void update() {

		if (Menu.chosen > menu.length - 1)
			Menu.chosen = 0;
		if (Menu.chosen < 0)
			Menu.chosen = menu.length - 1;

	}
}
