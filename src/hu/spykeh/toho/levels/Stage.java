package hu.spykeh.toho.levels;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.tile.Tile;

public class Stage {
	protected String scoreStr = "Score";
	protected int score = 0;
	Keyboard input;
	Player player;
	StageOne stageOne;
	public Stage(Keyboard input, Player player){
		this.input = input;
		this.player = player;
		stageOne = new StageOne();
	}
	public void render(Screen screen){
		Font.render(scoreStr,screen.width-80,20,screen,0xffffff);
		Font.render(String.valueOf(score), screen.width-80, 35, screen, 0xffffff);
		for(int i = 2*16 ; i < screen.width - 6*16 ; i = i + 16){
			for(int j = 16 ; j < screen.height - 2*16 ; j = j + 16){
				Tile.stone.render(i,j,screen);
			}
		}
		player.render(screen);
		stageOne.render(screen);
		
	}
	public void update(){
		player.update();
	}
}
