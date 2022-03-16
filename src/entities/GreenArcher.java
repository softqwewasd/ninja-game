package entities;

import java.awt.Graphics;

import level.Load;

public class GreenArcher extends Bot{

	private static String SPRITESHEET_PATH = "res/green_archer.gif";
	public GreenArcher(int x, int y, Entity target, Load loadedLevel) {
		super(x, y, target, SPRITESHEET_PATH, loadedLevel);
		setEntity(Entities.GreenArcher);
		// TODO Auto-generated constructor stub
		
	}

	


}
