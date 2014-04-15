package hu.spykeh.toho.levels;

import java.util.ArrayList;
import java.util.List;

import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.tile.Tile;

public class Stage {
	protected String scoreStr = "Score";
	protected int score = 0;
	public static int sizeX;
	public static int sizeY;
	public static int x;
	public static int y;
	Keyboard input;
	public ArrayList<PlayerMP> player;
	StageOne stageOne;
	public Stage(Keyboard input, ArrayList<PlayerMP> player){
		this.input = input;
		this.player = player;
		stageOne = new StageOne();
		x = 16;
		y = 16;
	}
	public void render(Screen screen){
		sizeX = screen.width - 7*16;
		sizeY = screen.height - 3*16;
		Font.render(scoreStr,screen.width-80,20,screen,0xffffff);
		Font.render(String.valueOf(score), screen.width-80, 35, screen, 0xffffff);
		for(int i = x ; i < sizeX + x ; i = i + 16){
			for(int j = y ; j < sizeY + y ; j = j + 16){
				Tile.stone.render(i,j,screen);
			}
		}
		for(PlayerMP p : player){
			p.render(screen);
		}
		
		stageOne.render(screen);
		
	}
	public void update(){
		for(PlayerMP p : player){
			p.update();
		}
		/*TODO: mindegyik projectilera ellenõrizze, hogy eltalál-e valamit, fontos tudni a forrást is.
		for(int i = 0 ; i < player.projectiles.size() ; i++){
			if(collision(player.projectiles.get(i),stageOne.mobs.get(0))){
				score++;
				player.projectiles.get(i).remove();
			}
		}*/
	}
	
	public boolean collision(Projectile p, Mob m){
		
		if(Math.abs(p.getY() - m.getY()) < m.hitbox && Math.abs(p.getX() - m.getX()) < m.hitbox){
			System.out.println("collided");
			return true;
		}else{
			return false;
		}
		
	}
}
