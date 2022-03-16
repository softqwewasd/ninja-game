package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Entities;
import level.Load;
import main.Animation;
import tiles.Tile;

public abstract class Entity {

	public int x;
	public int y;
	
	public int velocityX;
	public double velocityY;
	
	public boolean movingLeft;
	public boolean movingRight;
	public boolean jumping;
	public boolean falling;
	
	public boolean holdingJump = false;
	
	private int lastFacing;
	
	private Entities entity;
	
	public boolean shotArrow = false;
	public boolean stopAnimation = false;
	
	protected BufferedImage spriteSheet;
	
	protected Animation runningLeftAnimation;
	protected Animation runningRightAnimation;
	protected Animation idleLeftAnimation;
	protected Animation idleRightAnimation;
	
	protected Animation shootingLeftAnimation;
	protected Animation shootingRightAnimation;
	
	protected List<BufferedImage> runningLeftSprites = new ArrayList<>();
	protected List<BufferedImage> runningRightSprites = new ArrayList<>();
	protected List<BufferedImage> idleRightSprites = new ArrayList<>();
	protected List<BufferedImage> idleLeftSprites = new ArrayList<>();
	
	protected List<BufferedImage> shootingLeftSprites = new ArrayList<>();
	protected List<BufferedImage> shootingRightSprites = new ArrayList<>();
	
	public int lives = 5;

	public boolean isDead = false;
	
	public Entity(int x, int y, String spriteSheetPath){
		this.x = x;
		this.y = y;
		falling = true;
		try{
			spriteSheet = ImageIO.read(new File(spriteSheetPath));
		}catch(Exception e){
			e.printStackTrace();
		}
		runningLeftSprites.add(spriteSheet.getSubimage(108, 5, 30, 31));
		runningLeftSprites.add(spriteSheet.getSubimage(69, 8, 36, 28));
		runningLeftSprites.add(spriteSheet.getSubimage(34, 5, 31, 31));
		runningLeftSprites.add(spriteSheet.getSubimage(2, 8, 30, 28));
		
		runningRightSprites.add(spriteSheet.getSubimage(98, 129, 30, 31));
		runningRightSprites.add(spriteSheet.getSubimage(130, 132, 36, 28));
		runningRightSprites.add(spriteSheet.getSubimage(170, 129, 31, 31));
		runningRightSprites.add(spriteSheet.getSubimage(204, 132, 30, 28));
		
		idleRightSprites.add(spriteSheet.getSubimage(2, 126, 29, 34));
		idleRightSprites.add(spriteSheet.getSubimage(34, 126, 29, 34));
		idleRightSprites.add(spriteSheet.getSubimage(66, 126, 29, 34));
		
		idleLeftSprites.add(spriteSheet.getSubimage(204, 2, 29, 34));
		idleLeftSprites.add(spriteSheet.getSubimage(172, 2, 29, 34));
		idleLeftSprites.add(spriteSheet.getSubimage(140, 2, 29, 34));
		
		shootingLeftSprites.add(spriteSheet.getSubimage(202, 52, 32, 36));
		shootingLeftSprites.add(spriteSheet.getSubimage(166, 54, 31, 34));
		shootingLeftSprites.add(spriteSheet.getSubimage(129, 55, 33, 33));
		shootingLeftSprites.add(spriteSheet.getSubimage(90, 37, 36, 51));
		shootingLeftSprites.add(spriteSheet.getSubimage(55, 45, 31, 43));
		shootingLeftSprites.add(spriteSheet.getSubimage(17, 54, 34, 34));
		shootingLeftSprites.add(spriteSheet.getSubimage(200, 90, 34, 34));
		shootingLeftSprites.add(spriteSheet.getSubimage(162, 91, 37, 33));
		
		shootingRightSprites.add(spriteSheet.getSubimage(2, 176, 32, 36));
		shootingRightSprites.add(spriteSheet.getSubimage(39, 178, 31, 34));
		shootingLeftSprites.add(spriteSheet.getSubimage(74, 179, 33, 33));
		
		shootingRightSprites.add(spriteSheet.getSubimage(110, 161, 36, 51));
		shootingRightSprites.add(spriteSheet.getSubimage(150, 169, 31, 43));
		shootingRightSprites.add(spriteSheet.getSubimage(185, 178, 34, 34));
		shootingRightSprites.add(spriteSheet.getSubimage(2, 214, 34, 34));
		shootingRightSprites.add(spriteSheet.getSubimage(37, 215, 37, 33));
		
		setLastFacing(2);
		runningLeftAnimation = new Animation(3, runningLeftSprites, this);
		runningRightAnimation = new Animation(3, runningRightSprites, this);
		idleLeftAnimation = new Animation(5, idleLeftSprites, this);
		idleRightAnimation = new Animation(5, idleRightSprites, this);
		shootingLeftAnimation = new Animation(2, shootingLeftSprites, this);
		shootingRightAnimation = new Animation(2, shootingRightSprites, this);
		
	}
	
	protected void setEntity(Entities entity){
		this.entity = entity;
	}
	
	public Entities getEntity(){
		return entity;
	}
	
	public abstract void render(Graphics g);
	public abstract void tick(Load loadedLevel);
	
	public Rectangle bottomBounds(){
		return new Rectangle(x+17, y+32, 34, 32);
	}
	
	public Rectangle leftBounds(){
		return new Rectangle(x+10, y+2, 5, 60);
	}
	
	public Rectangle rightBounds(){
		return new Rectangle(x+53, y+2, 5, 60);
	}
	
	public Rectangle topBounds(){
		return new Rectangle(x+17, y, 34, 32);
	}
	
	public int getLastFacing(){
		return lastFacing;
	}
	
	public int getLives(){
		return lives;
	}
	
	protected void setLives(int lives){
		this.lives = lives;
	}
	
	public void setLastFacing(int i){
		if(i == 1 || i == 2 || i == 0){
			lastFacing = i;
		}else{
			System.out.println("couldnt set lastFacing - not 0 1 or 2");
		}
	}
	
}
