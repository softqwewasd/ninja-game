package entities;

import level.Load;

public class BlueArcher extends Bot{

	private static String SPRITESHEET_PATH = "res/blue_archer.gif";
	public BlueArcher(int x, int y, Entity target, Load loadedLevel) {
		super(x, y, target, SPRITESHEET_PATH, loadedLevel);
		// TODO Auto-generated constructor stub
		setEntity(Entities.BlueArcher);
	}

}
