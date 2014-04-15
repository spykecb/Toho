package hu.spykeh.toho.levels;

import java.util.ArrayList;
import java.util.List;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Screen;


public class Level{

	public Menu m;
	public Stage stage;
	public Keyboard input;
	private ArrayList<PlayerMP> player = new ArrayList<PlayerMP>();
	public Level(Keyboard input, PlayerMP player){
		this.input = input;
		m = new Menu(input);
		this.player.add(player);
		stage = new Stage(input, this.player);
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
	public void addPlayer(PlayerMP p){
		player.add(p);
	}

}
