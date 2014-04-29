package hu.spykeh.toho.levels;

import java.util.Iterator;

import hu.spykeh.toho.Jatek;
import hu.spykeh.toho.Keyboard;
import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Font;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.net.packets.Packet00Login;
import hu.spykeh.toho.sprites.Sprite;

public class ReadyMenu extends Menu{
	
	private String[] menu = { "Ready" };
	private String[] menuValue = { "" };
	public PlayerMP player;
	public ReadyMenu(Keyboard input) {
		super(input);
		
		player = new PlayerMP(StageMP.stage,Jatek.jatek.jatekosNev,Sprite.player,100,100,Jatek.jatek.keyboard,(Jatek.jatek.server != null) ? 0 : 1,null,-1);
		StageMP.stage.addPlayer(player);
		System.out.println("multi játékos hozzáadva");
		Packet00Login loginPacket = new Packet00Login(player.getName(),(int)player.getX(),(int)player.getY(),player.getSide());
		if(Jatek.jatek.server != null){ //szerverként hozzáadjuk a játékost
			Jatek.jatek.server.addConnection(player, loginPacket);
		}
			
		loginPacket.writeData(Jatek.jatek.client); //szervernek küldjük az adatot
		chosen = 0;
	}

	public void render(Screen screen) {
		renderBG(screen);
		renderStrings(screen, menu, menuValue);
		int pos = 40;
		int color;
		Iterator<PlayerMP> it = StageMP.stage.players.iterator();
		while(it.hasNext()){
			PlayerMP p = it.next();
		
			if(p.isReady()){
				color = 0x7f28f7;
			}else{
				color = 0xffffff;
			}
			Font.render(p.getName(), pos, 30, screen, color);
			pos += p.getName().length()*8+8;
		}
	}

	public void update() {
		if(Jatek.state == 0){
			switchToGame();
		}
		if (Menu.chosen > menu.length - 1)
			Menu.chosen = 0;
		if (Menu.chosen < 0)
			Menu.chosen = menu.length - 1;

	}
	
	public void switchToGame(){
		int counter = 0;
		for(PlayerMP p : StageMP.stage.players){
			if(p.isReady()){
				counter++;
			}
		}
		if(counter == 2){
			StageMP.gameStarted = true;
			Jatek.state = 1;
			
		}
		counter = 0;
	}
}
