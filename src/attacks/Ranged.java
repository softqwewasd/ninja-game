package attacks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entities;
import level.Load;
import tiles.Tile;

public class Ranged extends Attack{
	
	final private static String SPRITE_PATH = "res/arrow.png";
	
	private double angle;
	
	private long ms;
	
	Load load;
	Line2D line;
	public boolean inMotion = true;
	
	
	public Ranged(int x, int y, double angle, long ms, Load load, Entities entity) {
		super(x, y);
		this.ms = ms;
		this.load = load;
		line = new Line2D.Double(getInitialBoundPoint(), getFinalBoundPoint());
		try {
			setSprite(ImageIO.read(new File(SPRITE_PATH)).getSubimage(12, 33, 29, 3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.angle = angle;
		if(entity == Entities.BlackArcher){
			velocityX = 15*Math.cos(-angle);
			velocityY = 15*Math.sin(-angle);
			
		}else if(entity == Entities.GreenArcher){
			velocityX = 10*Math.cos(-angle);
			velocityY = 10*Math.sin(-angle);
			
		}else if(entity == Entities.YellowArcher){
			velocityX = 8*Math.cos(-angle);
			velocityY = 8*Math.sin(-angle);
		}else if(entity == Entities.BlueArcher){
			velocityX = 6*Math.cos(-angle);
			velocityY = 6*Math.sin(-angle);
		}
	}
	
	public void tick(){
		
		if(inMotion){
			x+=velocityX;
			y+=velocityY;
			
		}
		for(Tile tile : load.getTiles()){
			if(getBounds().intersects(tile.getBounds())){
				inMotion = false;
			}
		}
	}
	
	@Override
	public void render(Graphics g){
	
		
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform backup = g2d.getTransform();
		AffineTransform transform = new AffineTransform();
		transform.rotate(-angle, x, y);
		
		g2d.transform(transform);
		
		g.drawImage(getSprite(), (int)x, (int)y, 58, 6, null);
		g2d.setTransform(backup);
		/*
		g.setColor(Color.GREEN);
		g2d.drawLine(getInitialBoundPoint().x, getInitialBoundPoint().y, getFinalBoundPoint().x, getFinalBoundPoint().y);
*/
	}
	
	
	public Line2D getBounds(){
		return new Line2D.Double(getInitialBoundPoint(), getFinalBoundPoint());
	}
	
	private Point getInitialBoundPoint(){
		return new Point((int)x, (int)y);
	}
	
	private Point getFinalBoundPoint(){ 
		return new Point((int) ((int)(58*Math.cos(-angle))+x), (int)(58*Math.sin(-angle)+y));
	}
	
	public double getAngle(){
		return angle;
	}
	
	public long getMs(){
		return ms;
	}
	
	
	
}
