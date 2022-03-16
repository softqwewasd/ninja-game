package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

public class HalfThiccStone extends Tile{

	public HalfThiccStone(int x, int y) {
		super(x, y);
		setSprite(new File(Tile.MAIN_TILESET_PATH), 10);
	}

	public void render(Graphics g) {
		g.drawImage(getSprite(), x, y, getWidth(), getHeight(), null);
		Graphics2D g2d = (Graphics2D) g;

		
	}
	
}
