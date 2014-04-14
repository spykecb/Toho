package hu.spykeh.toho;

import hu.spykeh.toho.levels.Menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	private boolean[] keys = new boolean[120];
	public boolean up,down,left,right,enter;
	public boolean y;
	public void update(){
		up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]; 
		down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]; 
		left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]; 
		right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]; 
		y = keys[KeyEvent.VK_Y];
		enter = keys[KeyEvent.VK_ENTER];

	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(Jatek.state == 0 && e.getKeyCode() == KeyEvent.VK_UP){
			Menu.chosen--;
		}else if(Jatek.state == 0 && e.getKeyCode() == KeyEvent.VK_DOWN){
			Menu.chosen++;
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
