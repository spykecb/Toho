package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;

public class Menu{
	private String[] menu = {"Start","Options","Quit"};
	Keyboard input;
	public static int chosen = 0;
	public Menu(Keyboard input){
		this.input = input;
	}

	public void render(Screen screen) {
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				screen.pixels[x + y * screen.width] = 0xff0000;
			}
		}
		int color;
		for(int i = 0 ; i < menu.length ; i++){
			if(i == chosen)
				color = 0xffffff;
			else
				color = 0x000000;
			Font.render(menu[i], 20, screen.height / 2 + i*20, screen, color);
		}
	}
	
	public void update(){
		if(input.enter && Jatek.state == 0){
			if(chosen == 0)
				System.out.println("Stage-re lépés");
				Jatek.state = 1;
			if(chosen == 2){
				
			}
				
		}
		if(chosen > menu.length-1)chosen = 0;
		if(chosen < 0)chosen = menu.length-1;
		
	}
}
