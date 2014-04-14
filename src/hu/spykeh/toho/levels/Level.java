package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.gfx.Screen;


public class Level{

	public Menu m;
	public Stage stage;
	Keyboard input;
	Player player;
	public Level(Keyboard input){
		this.input = input;
		m = new Menu(input);
		player = new Player("spykeh",100,150,input);
		stage = new Stage(input, player);
	}
	public void render(Screen screen){
		
		if(Jatek.state == 0){
			m.render(screen);
		}else if(Jatek.state == 1){
			stage.render(screen);
		}
	}
	
	public void update(){
		if(Jatek.state == 0){
			m.update();
		}else if(Jatek.state == 1){
			stage.update();
		}
		
	}
	

}
