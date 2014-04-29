package hu.spykeh.toho;


import hu.spykeh.toho.entities.Player;
import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.levels.Menu;
import hu.spykeh.toho.levels.Stage;
import hu.spykeh.toho.levels.StageMP;
import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;
import hu.spykeh.toho.net.packets.Packet05Ready;
import hu.spykeh.toho.sprites.Sprite;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Formatter;

import javax.swing.JOptionPane;

public class Keyboard implements KeyListener{
	
	private boolean[] keys = new boolean[120];
	public boolean up,down,left,right,enter,esc,shift;
	public boolean y;
	public void update(){
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]; 
		down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]; 
		left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]; 
		right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]; 
		y = keys[KeyEvent.VK_Y];
		esc = keys[KeyEvent.VK_ESCAPE];
		shift = keys[KeyEvent.VK_SHIFT];

	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(Jatek.state == 0){
			if(e.getKeyCode() == KeyEvent.VK_UP){
				Menu.chosen--;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				Menu.chosen++;
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				switch(Menu.type){
				case MAINMENU:
					if(Menu.chosen == 0){
						Menu.type = Menu.menuType("10");
					} else if(Menu.chosen == 1){
						Menu.type = Menu.menuType("01");
					}
					break;
				case OPTIONS:
					if(Menu.chosen == 0){
						Jatek.jatek.jatekosNev = JOptionPane.showInputDialog(Jatek.jatek,"Name");
						Formatter x;
						try {
							x = new Formatter("config.cfg");
							x.format("%s%s", "Name = ", Jatek.jatek.jatekosNev);
							System.out.println("name elmentve");
							x.close();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						
					}
					break;
				case STARTMENU:
					if(Menu.chosen == 0){
						Jatek.offline = true;
						Stage.stage.player = new Player(Stage.stage,Jatek.jatek.jatekosNev,Sprite.player,100,100,Jatek.jatek.keyboard,0);
						Stage.stage.players.add(Stage.stage.player);
						Jatek.state = 1;
					}
					if(Menu.chosen == 1){
						Jatek.offline = false;
						Jatek.jatek.server = new GameServer(Jatek.jatek);
						Jatek.jatek.server.start();
						Jatek.jatek.client = new GameClient(Jatek.jatek,"localhost");
						Jatek.jatek.client.start();
						Menu.type = Menu.menuType("20");
						
					}else if(Menu.chosen == 2){
						Jatek.offline = false;
						Jatek.jatek.client = new GameClient(Jatek.jatek,"localhost");
						Jatek.jatek.client.start();
						Menu.type = Menu.menuType("20");
						
					}
					break;
				case READYMENU:
					if(Menu.chosen == 0){
						PlayerMP player = Jatek.jatek.level.menu.readyMenu.player;
						player.setReady(true);
						Packet05Ready packet = new Packet05Ready(player.getName());
						packet.writeData(Jatek.jatek.client);
					}
					break;
				case GAMEEND:
					if(Menu.chosen == 0){
						Menu.type = Menu.menuType("00");
					}
					break;
				default:
					break;
				}
				
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				switch(Menu.type){
				case MAINMENU:
					Menu.chosen = 2;
					break;
				case OPTIONS:
					Menu.type = Menu.menuType("00");
					break;
				case STARTMENU:
					Menu.type = Menu.menuType("00");
					break;
				default:
					break;
				}
			}
		}
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
}
