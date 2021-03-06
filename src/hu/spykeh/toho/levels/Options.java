package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Screen;


public class Options extends Menu {

	private String[] menu = {"Name:"};
	private String[] menuValue = {""};
	public Options(Keyboard input){
		super(input);
		chosen = 0;
	}

	public void render(Screen screen) {
		renderBG(screen);
		renderStrings(screen, menu, menuValue);

	}

	public void update() {
		menuValue[0] = Jatek.jatek.jatekosNev;
		if (Menu.chosen > menu.length - 1)
			Menu.chosen = 0;
		if (Menu.chosen < 0)
			Menu.chosen = menu.length - 1;

	}
}
