package attacks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import level.Load;
import tiles.Tile;

public class Spell extends Attack{
	
	final public static int WIDTH = 64;
	final public static int HEIGHT = 64;
	
	
	public boolean usingAngle = false;
	private double angle;
	
	public static long lastTimeShot;
	
	public Spell(int x, int y, BufferedImage sprite, double angle) {
		super(x, y);
		// TODO Auto-generated constructor stub
		setSprite(sprite);
		usingAngle = true;
		this.angle = angle;
		velocityX = 22*Math.cos(-angle);
		velocityY = 22*Math.sin(-angle);
		
		y += -HEIGHT/2;
		lastTimeShot = System.currentTimeMillis();
		
	}
	public Spell(int x, int y, BufferedImage sprite, double velocityX, double velocityY) {
		super(x, y);
		// TODO Auto-generated constructor stub
		setSprite(sprite);
		usingAngle = false;
		
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		lastTimeShot = System.currentTimeMillis();
	}
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getSprite(), (int)x, (int)y, WIDTH, HEIGHT, null);
		g.setColor(Color.RED);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(getBounds());
	}

	public void tick(Load loadedLevel) {
		// TODO Auto-generated method stub

		x += velocityX;
		y += velocityY;
		if(!usingAngle){
			velocityY += 1;
		}
		
		for(Tile tile : loadedLevel.getTiles()){
			if(tile.getBounds().intersects(getBounds())){
				velocityX = 0;
				velocityY = 0;
			}
		}
		
		
	}

	public Rectangle getBounds(){
		return new Rectangle((int)x+13, (int)y, WIDTH-26, HEIGHT);
	}

	
	
}
