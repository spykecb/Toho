package hu.spykeh.toho.levels;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.net.packets.Packet00Login;
import hu.spykeh.toho.net.packets.Packet01Disconnect;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.tile.Tile;

public class Stage {
	protected String scoreStr = "Score";
	protected int score = 0;
	public static int sizeX;
	public static int sizeY;
	public static int x;
	public static int y;
	PlayerMP player;
	Jatek jatek;
	public ArrayList<PlayerMP> players = new ArrayList<PlayerMP>();
	StageOne stageOne;
	public Stage(Jatek jatek){
		this.jatek = jatek;
		player = new PlayerMP(this,jatek.jatekosNev,100,100,jatek.keyboard,null,-1);
		addPlayer(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername(),player.getX(),player.getY());
		if(jatek.server != null){ //szerverként hozzáadjuk a játékost
			jatek.server.addConnection(player, loginPacket);
		}
		
		loginPacket.writeData(jatek.client); //szervernek küldjük az adatot
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
		for(PlayerMP p : players){
			p.render(screen);
			Font.render(p.getUsername(), p.getX()-10, p.getY()-8, screen, 0xffffff);
		}
		
		stageOne.render(screen);
		
	}
	public void update(){
		int index = 0;
		boolean shouldDelete = false;
		for(PlayerMP p : players){
			p.update();
			if(!p.online){
				shouldDelete = true;
				break;
			}
			index++;
		}
		if(shouldDelete)
			removePlayer(index);
		shouldDelete = false;
		
		
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
	
	public void addPlayer(PlayerMP p){
		players.add(p);
	}
	
	/*GameCLientnél használjuk fel*/
	public void removePlayer(String username){
		int index = 0;
		for(PlayerMP p : players){
			if(p.getUsername().equals(username)){
				break;
			}
			index++;
		}
		players.remove(index);
	}
	
	/*Megadja a törlendõ játékos ID-jét a játékosok arrayból*/
	public void removePlayer(int index){
		Packet01Disconnect packet = new Packet01Disconnect(players.get(index).getUsername());
		players.remove(index);
		packet.writeData(jatek.client);
			
	}
	
	public int getPlayerMPIndex(String username){
		int index = 0;
		for(PlayerMP p : players){
			if(p.getUsername().equals(username)){
				break;
			}
			index++;
		}
		return index;
	}
	
	/*Megmozgatja az adott játékost*/
	public void movePlayer(String username, int x, int y){
		int index = getPlayerMPIndex(username);
		players.get(index).setPos(x, y);
	}
	
	public void setShooting(String username, boolean shooting){
		int index = getPlayerMPIndex(username);
		players.get(index).setShooting(shooting);;
	}
}
