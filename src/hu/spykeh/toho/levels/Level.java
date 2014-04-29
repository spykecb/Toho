package hu.spykeh.toho.levels;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Screen;


public class Level{

	public Menu menu;
	public StageMP stageMP;
	public Stage stage;
	public Keyboard input;
	PlayerMP player;
	Jatek jatek;
	public Level(Keyboard input,Jatek jatek){
		stageMP = new StageMP(jatek);
		stage = new Stage(jatek);
		
		this.jatek = jatek;
		this.input = input;
		menu = new Menu(input);
	}
	
	public void render(Screen screen){
		if(Jatek.state == 0){
			menu.render(screen);
			
			/*
			if(stage != null){
				stage = null;
				System.out.println("Stage kitörölve");
			}*/
		}else if(Jatek.state == 1){
			if(Jatek.offline){
				stage.render(screen);
			}else{
				stageMP.render(screen);
			}
			
		}else{
			System.out.println("wrong state brah");
		}
	}

	public void update(){
		if(input.enter){
			System.out.println("enter");
		}
		if(Jatek.state == 0){
				menu.update();
		}else if(Jatek.state == 1){
			if(Jatek.offline){
				stage.update();
			}else{
				stageMP.update();
			}
		}
		
	}

}
