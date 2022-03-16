package entities;

import level.Load;

public class YellowArcher extends Bot{

	private static String SPRITESHEET_PATH = "res/yellow_archer.gif";
	public YellowArcher(int x, int y, Entity target, Load loadedLevel) {
		super(x, y, target, SPRITESHEET_PATH, loadedLevel);
		// TODO Auto-generated constructor stub¨
		setEntity(Entities.YellowArcher);
	}

}
