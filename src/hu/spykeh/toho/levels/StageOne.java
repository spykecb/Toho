package hu.spykeh.toho.levels;

import java.util.ArrayList;

import hu.spykeh.toho.entities.Mob;
import hu.spykeh.toho.gfx.Screen;

public class StageOne {
	
	public ArrayList<Mob> mobs = new ArrayList<Mob>();
	public StageOne(){
		mobs.add(new Mob("Mob01",100,50,null));
	}
	
	public void render(Screen screen){
		mobs.get(0).render(screen);
		
	}
	public void update(){
		
	}
}
