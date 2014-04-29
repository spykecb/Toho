package hu.spykeh.toho.levels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import hu.spykeh.animations.projAnimations.ProjectileAnimation;
import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.net.packets.Packet04MobSpawn;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.sprites.Sprite;
import hu.spykeh.toho.tile.Tile;

public class StageOne{
	protected String scoreStr = "Score";
	protected String healthStr = "Health";
	protected int score = 0;
	private boolean active = true;
	public boolean sentSpawnRequest = false;
	private boolean sendRequest;
	private boolean firstMobSpawned = false;
	public List<Mob> mobs = new ArrayList<Mob>();
	public int sizeX ;
	public int sizeY;
	public int x;
	public int y;
	public Player player;
	private int difficulty = 0;
	private int side ;
	public StageOne(Jatek jatek,int side, int x, int y,Player player){
		this.x = x;
		this.y = y;
		this.side = side;
		this.player = player;
		sizeX = Jatek.jatek.screen.width/2 - 2* 16;
		sizeY = Jatek.jatek.screen.height - 3*16;
		init();

	}
	public void init(){
		player.setHp(10);
		score = 0;
		System.out.println("uj stageone létrehozva");
	}
	public void render(Screen screen){
		if(player != null){
			Font.render(scoreStr,x+sizeX-30,y-16,screen,0xffffff);
			Font.render(String.valueOf(score), x+sizeX-30, y-8, screen, 0xffffff);
			Font.render(healthStr,x+30,y-16,screen,0xffffff);
			Font.render(String.valueOf(player.getHp()), x+30, y-8, screen, 0xffffff);
			Font.render(player.getName(), x, sizeY + 24, screen,0xffffff);
		}
		for(int i = x ; i < sizeX + x ; i = i + 16){
			for(int j = y ; j < sizeY + y ; j = j + 16){
				Tile.stone.render(i,j,screen);
			}
		}
		Iterator<Mob> it = mobs.iterator();
		while(it.hasNext()){
			Mob mob = it.next();
			mob.render(screen);
		}
		
	}
	
	public void update(){
		if(player != null){
			collisionCheck(this.player);
			if(player.getHp() == 0){
				Jatek.state = 0;
			}
		}
		if(!firstMobSpawned){
			spawnSystem();
			firstMobSpawned = true;
		}else if(mobs.size() == 0){
			spawnSystem();
		}
		if(Jatek.offline){
			
		}else{
			
		}
		
		Iterator<Mob> it = mobs.iterator();
		while(it.hasNext()){
			Mob mob = it.next();
			if(mob.getHp() < 0){
				it.remove();
				score++;
				continue;
			}
			collisionCheck(mob);
			mob.update();
		}
	}
	

	
	public void spawnSystem() {
		
		
		
			if(Jatek.offline){
				Random r = new Random();
				int mobX = r.nextInt(sizeX)+16;
				spawnMobs(new Mob(Stage.stage,"mob", Sprite.mob1, mobX,70,getSide()));
				System.out.println("side:" + getSide() + "      x:" + mobX);
			}else{
				if(player.input != null){
					sendRequest = true;
					for(PlayerMP p : StageMP.stage.players){
						if(!p.isLoaded()){
							sendRequest = false;
							System.out.println(p.getName() + " isn't loaded");
						}else{
							System.out.println(p.getName() + " is loaded");
						}
					}
					if(sendRequest){
						Random r = new Random();
						int mobX = r.nextInt(sizeX)+16;
						spawnMobs(new Mob(StageMP.stage,"mob", Sprite.mob1, mobX,70,getSide()));
						System.out.println("sent:" + getSide() + "    x:" + mobX);
					}
				}
			}
			
		
	}

	public void spawnMobs(Mob m){
	
			
			if(player != null && !Jatek.offline && !sentSpawnRequest){
				Packet04MobSpawn packet = new Packet04MobSpawn(m.getName(),(int)m.getX(),(int)m.getY(),m.getHp(),getSide());
				packet.writeData(Jatek.jatek.client);
				sentSpawnRequest = true;
			}else if(Jatek.offline){

				mobs.add(m);
			}
			
		
	}
	
	/*Adott játékos projectile-jaira ellenõrzi mit talált el.
	 * */
	public void collisionCheck(Player p){
		for(ProjectileAnimation projanim : p.getProjAnim()){
			Iterator<Projectile> it = projanim.getProjectiles().iterator();
			while(it.hasNext()){
				Projectile proj = it.next();
				//System.out.println(p.getName() + " :" + proj.getX() + "," + proj.getY());
				for(Mob m : mobs){
					double a = player.getY() - m.getY();
					double b = player.getX()- m.getX();
					player.setShootingAngle(Math.atan2(b,a) + Math.PI);
					if(StageMP.stage.collision(proj,m)){
						m.setHp(m.getHp() - 1);
						it.remove();
						break;
					}
				}
			}
		}
	}
	
	/*Adott MOB projectile-jaira ellenõrzi mit talált el.
	 * */
	public void collisionCheck(Mob m){
		for(ProjectileAnimation projanim : m.getProjAnim()){
			Iterator<Projectile> it = projanim.getProjectiles().iterator();
			while(it.hasNext()){
				Projectile proj = it.next();
					if(StageMP.stage.collision(proj,player)){
						player.setHp(player.getHp()-1);
						it.remove();
						break;
					}
				
			}
		}
	}
	
	public int getSide(){
		return this.side;
	}
	
	public void setActive(boolean b) {
		this.active = b;
		
	}

	public boolean isActive() {
		return this.active;
	}
	
}

