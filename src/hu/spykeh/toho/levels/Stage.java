package hu.spykeh.toho.levels;

import java.util.ArrayList;
import java.util.List;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.entities.PlayerBot;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;


public class Stage {
	public int mode;
	Jatek jatek;
	
	public Player player;
	public PlayerBot playerBot;
	public List<Player> players = new ArrayList<Player>();
	public static Stage stage;
	private boolean readyToSpawn = true;
	private boolean stageCreated = false;
	private boolean gotWinner = false;
	protected StageOne stageOne1;
	protected StageOne stageOne2;
	protected int diff;
	public static boolean gameStarted = false;
	public Stage(Jatek jatek){
		this.jatek = jatek;
		stage = this;

		playerBot = new PlayerBot(this,"BOT", Sprite.player,100,100,null,1);
		
		players.add(playerBot);
		
	}
	public void render(Screen screen){
		
		if(stageOne1 != null){
			stageOne1.render(screen);
			stageOne2.render(screen);
		}
		
		for(Player p : players){
			p.render(screen);
		}
		
	}
	public void update(){
		if(!stageCreated){
			System.out.println("Stage létrehozva");
			System.out.println("Offline");
			stageOne1 = new StageOne(jatek,0,16,16,player);
			stageOne2 = new StageOne(jatek,1,Jatek.width/2,16,playerBot);	
			diff = stageOne2.x - stageOne1.x;
			stageCreated = true;
			
		}else{
			stageOne1.update();
			stageOne2.update();
		}
		
		
		for(Player p : players){
			p.update();
			if(p.getHp() == 0){
				gotWinner = true;
				break;
			}
		}
		if(gotWinner){
			for(Player p : players){
				if(p.getHp() != 0){
					Jatek.state = 0;
					Menu.type = Menu.menuType("30");
					Jatek.jatek.level.menu.winner = p.getName();
					System.out.println("Játék vége -> main menu");
					removeStages();
				}
			}
			
		}

	}
	
	public boolean collision(Projectile p, Mob m){
		int px1 = (int)p.getX() + p.hitboxFromX;
		int py1 = (int)p.getY() + p.hitboxFromY;
		int px2 = (int)p.getX() + p.hitboxToX;
		int py2 = (int)p.getY() + p.hitboxToY;
		int mx1 = (int)m.getX() + m.hitboxFromX;
		int my1 = (int)m.getY() + m.hitboxFromY;
		int mx2 = (int)m.getX() + m.hitboxToX;
		int my2 = (int)m.getY() + m.hitboxToY;
		if(px2 > mx1 && px2 < mx2 && py1 < my2 && py1 > my1 ||
			px1 < mx2 && px1 > mx1 && py1 < my2 && py1 > my1 || 
			px2 > mx1 && px2 < mx2 && py2-2 > my1 && py2 < my2 || 
			px1 < mx2 && px1 > mx1 && py2 > my1 && py2 < my2){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	public StageOne getStageOne(int i){
		if(i == 0){
			return this.stageOne1;
		}else return this.stageOne2;
	}
	
	public void removeStages(){
		stageOne1 = null;
		stageOne2 = null;
		stageCreated = false;
	}
	public int getDiff(){
		return this.diff;
	}

}
