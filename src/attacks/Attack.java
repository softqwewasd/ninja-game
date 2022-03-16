package attacks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class Attack {
	
	
	public double x;
	public double y;
	
	public double velocityX;
	public double velocityY;
	
	private BufferedImage sprite;
	
	
	public Attack(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(Graphics g);
	
	public BufferedImage getSprite(){
		 return sprite;
	}
	
	protected void setSprite(BufferedImage sprite){
		this.sprite = sprite;
	}
	
}
