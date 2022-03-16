package attacks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Entity;
import level.Load;

public class Attacks {
	
	// only for the player
	public static List<Ranged> ranged = new ArrayList<>();
	// Only for the boss 
	public static List<Spell> spells = new ArrayList<>();
	
	public static Ranged lastRangedShot;
	
	
	public void tick(Entity mainEntity, Load loadedLevel){
		for(Ranged ranged: new ArrayList<Ranged>(ranged)){
			ranged.tick();
			
		}
		for(Spell spell : new ArrayList<Spell>(spells)){

			spell.tick(loadedLevel);
		}
		if(ranged.size() > 10){
			ranged.remove(10);
		}
		
		
	}
	
	public void render(Graphics g){
		for(Ranged ranged: new ArrayList<Ranged>(ranged)){
			ranged.render(g);
		}
		for(Spell spell : new ArrayList<Spell>(spells)){
			spell.render(g);
		}
		
	}
}
