package hu.spykeh.toho.levels;

import javax.swing.JOptionPane;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Screen;


public class Level{

	public Menu m;
	public Stage stage;
	public Keyboard input;
	PlayerMP player;
	Jatek jatek;
	public Level(Keyboard input,Jatek jatek){
		this.jatek = jatek;
		this.input = input;
		m = new Menu(input);
	}
	
	public void render(Screen screen){
		
		if(Jatek.state == 0){
			m.render(screen);
			/*
			if(stage != null){
				stage = null;
				System.out.println("Stage kitörölve");
			}*/
		}else if(Jatek.state == 1){
			if(stage == null){
				stage = new Stage(jatek);
				System.out.println("Stage elinditva");
			}
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
