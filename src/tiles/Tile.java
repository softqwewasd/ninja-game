package tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Tile {
	
	final public static int DEFAULT_WIDTH = 32;
	final public static int DEFAULT_HEIGHT = 32;
	
	public int x;
	public int y;
	
	private int width;
	private int height;
	
	private BufferedImage sprite;
	
	private Tiles tileType;
	
	private Rectangle bounds;
	
	public Rectangle leftBounds;
	public Rectangle rightBounds;
	public Rectangle bottomBounds;
	public Rectangle topBounds;
	
	final public static String MAIN_TILESET_PATH = "res/main-tileset/JnRTiles.png";
	
	public Tile(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.leftBounds = new Rectangle(x, y+3, 6, Tile.DEFAULT_HEIGHT-3);
		this.topBounds = new Rectangle(x, y, Tile.DEFAULT_WIDTH, 6);
		this.bottomBounds = new Rectangle(x, y+Tile.DEFAULT_HEIGHT-6, Tile.DEFAULT_WIDTH, 6);
		this.rightBounds = new Rectangle(x+Tile.DEFAULT_WIDTH-3, y, 3, Tile.DEFAULT_HEIGHT);
		setWidth(width);
		setHeight(height);
		setBounds();
		
	}
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
		this.leftBounds = new Rectangle(x, y+6, 6, Tile.DEFAULT_HEIGHT-12);
		this.topBounds = new Rectangle(x, y, Tile.DEFAULT_WIDTH, 6);
		this.bottomBounds = new Rectangle(x, y+Tile.DEFAULT_HEIGHT-6, Tile.DEFAULT_WIDTH, 6);
		this.rightBounds = new Rectangle(x+Tile.DEFAULT_WIDTH-6, y+6, 6, Tile.DEFAULT_HEIGHT-12);
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		setBounds();
	}
	
	protected void setSprite(File file, int col){
		try {
			sprite = ImageIO.read(file).getSubimage(32*(col-1), 0, 32, 32);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(){
		return sprite;
	}
	
	protected void setTileType(Tiles tileType){
		this.tileType = tileType;
	}
	
	public Tiles getTile(){
		return tileType;
	}
	
	private void setWidth(int width){
		if(width > 0 && width % 2 == 0 && width <= 128){
			this.width = width;
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	private void setHeight(int height){
		if(height > 0 && height % 2 == 0 && height <= 128){
			this.height = height;
		}
	}
	
	public int getHeight(){
		return height;
	}
	
	private void setBounds(){
		bounds = new Rectangle(x, y, width, height);
	}	
		
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	public abstract void render(Graphics g);
	
}
