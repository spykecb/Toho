package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.ImageLoader;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.net.packets.Packet.PacketTypes;

public class Menu {
	Keyboard input;
	public static int chosen;
	public MainMenu mainMenu;
	public StartMenu startMenu;
	public Options options;
	public ReadyMenu readyMenu;
	public GameEnd gameEnd;
	public String winner;
	public static Menus type = menuType("00");
	public static enum Menus{
		INVALID(-1), MAINMENU(00), OPTIONS(01), STARTMENU(10), READYMENU(20), GAMEEND(30);
		
		private int id;
		private Menus(int id){
			this.id = id;
		}
		
		public int getId(){
			return id;
		}
	}
	public Menu(Keyboard input) {
		this.input = input;
		chosen = 0;
	}

	public void render(Screen screen) {
		
		switch(type){
		case MAINMENU:
			if (mainMenu == null) {
				mainMenu = new MainMenu(input);
			}
			mainMenu.render(screen);
			break;
		case OPTIONS:
			if (options == null) {
				options = new Options(input);
			}
			options.render(screen);
			
			break;
		case STARTMENU:
			if (startMenu == null) {
				startMenu = new StartMenu(input);
			}
			startMenu.render(screen);
			break;
		case READYMENU:
			if (readyMenu == null) {
				readyMenu = new ReadyMenu(input);
			}
			readyMenu.render(screen);
			break;
		case GAMEEND:
			if(gameEnd == null){
				gameEnd = new GameEnd(input,winner);
			}
			gameEnd.render(screen);
		default:
			break;
		}
		
	}

	public void renderBG(Screen screen) {
		screen.renderImage(0, 0, ImageLoader.bg);
	}

	public void renderStrings(Screen screen, String[] stringArray,String[] valueArray) {
		int color;
		for (int i = 0; i < stringArray.length; i++) {
			if (i == chosen)
				color = 0x7f28f7;
			else
				color = 0xffffff;
			Font.render(stringArray[i], 20, screen.height / 2 + i * 20, screen,
					color);
			Font.render(valueArray[i], 80, screen.height / 2 + i * 20, screen,
					0xffffff);
		}
	}
	
	public void update() {
		switch(type){
		case MAINMENU:
			if (mainMenu != null) {
				mainMenu.update();
			}
			break;
		case OPTIONS:
			if (options != null){
				options.update();
			}
			break;
		case STARTMENU:
			if (startMenu != null){
				startMenu.update();
			}
			break;
		case READYMENU:
			if (readyMenu != null) {
				readyMenu.update();
			}
			break;
		case GAMEEND:
			if(gameEnd == null){
				gameEnd = new GameEnd(input,winner);
			}
			gameEnd.update();
		default:
			break;
		}

	}
	
	public static Menus menuType(String Id){
		try{
			return menuType(Integer.parseInt(Id));
		}catch(NumberFormatException e){
			return Menus.INVALID;
		}
	}
	public static Menus menuType(int id){
		for(Menus m : Menus.values()){
			if(id == m.getId()){
				return m;
			}
		}
		return Menus.INVALID;
	}
	
}
