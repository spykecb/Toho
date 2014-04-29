package hu.spykeh.toho.levels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.entities.PlayerBot;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;
import hu.spykeh.toho.net.packets.Packet00Login;
import hu.spykeh.toho.net.packets.Packet01Disconnect;
import hu.spykeh.toho.net.packets.Packet06ReadyToStart;
import hu.spykeh.toho.projectile.Projectile;
import hu.spykeh.toho.tile.Tile;

public class StageMP extends Stage{

	public int mode;
	Jatek jatek;
	public boolean gotWinner = false;
	public List<PlayerMP> players = new ArrayList<PlayerMP>();
	public static StageMP stage;
	private boolean readyToSpawn = true;
	private boolean stageCreated = false;
	public static boolean gameStarted = false;
	public StageMP(Jatek jatek){
		super(jatek);
		this.jatek = jatek;
		stage = this;
		
		
	}
	
	public void render(Screen screen){
		
		if(stageOne1 != null){
			stageOne1.render(screen);
			stageOne2.render(screen);
		}
		
		Iterator<PlayerMP> it = players.iterator();
		while(it.hasNext()){
			PlayerMP p = it.next();
			p.render(screen);
		}
		
	}
	public void update(){
		if(!stageCreated){
			System.out.println("stage:" + this);
			System.out.println("Stage létrehozva");
			System.out.println("Multiplayer");
			stageOne1 = new StageOne(jatek,0,16,16,players.get(0).getSide() == 0 ? players.get(0) : players.get(1));
			stageOne2 = new StageOne(jatek,1,Jatek.width/2,16,players.get(0).getSide() == 1 ? players.get(0) : players.get(1));
			players.get(getPlayerMPIndex(Jatek.jatek.jatekosNev)).setLoaded(true);
			Packet06ReadyToStart packet = new Packet06ReadyToStart(Jatek.jatek.jatekosNev);
			packet.writeData(Jatek.jatek.client);
			diff = stageOne2.x - stageOne1.x;
			stageCreated = true;
		}else{
			stageOne1.update();
			stageOne2.update();
		}
		Iterator<PlayerMP> it = players.iterator();
		while(it.hasNext()){
			PlayerMP p = it.next();
			p.update();
			if(!p.online){
				it.remove();
			}
			if(p.getHp() == 0){
				gotWinner = true;
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
	

	
	public void addPlayer(PlayerMP p){
		players.add(p);
	}
	
	/*GameCLientnél használjuk fel*/
	public void removePlayer(String username){
		Iterator<PlayerMP> it = players.iterator();
		while(it.hasNext()){
			PlayerMP p = it.next();
			if(p.getName().equals(username)){
				it.remove();
			}
		}
	}
	
	/*Megadja a törlendõ játékos ID-jét a játékosok arrayból*/
	public void removePlayer(int index){
		players.remove(index);
		if(Jatek.jatek.client != null){
			Packet01Disconnect packet = new Packet01Disconnect(players.get(index).getName());
			packet.writeData(jatek.client);
		}
			
	}
	
	public int getPlayerMPIndex(String username){
		int index = 0;
		Iterator<PlayerMP> it = players.iterator();
		while(it.hasNext()){
			PlayerMP p = it.next();
			if(p.getName().equals(username)){
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
