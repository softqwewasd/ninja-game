package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import attacks.Attacks;
import attacks.Ranged;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import level.Load;
import main.Animation;
import main.Game;
import tiles.Tile;

public class BlackArcher extends Entity{
	
	private Load loadedLevel;
	
	public boolean movable;
	final private static String SPRITESHEET_PATH = "res/ninjatest.gif";
	
	private List<Ranged> temporaryEnemyArrowListToRemove = new ArrayList<>();
	
	
	public BlackArcher(int x, int y){
		super(x, y, SPRITESHEET_PATH);
		setEntity(Entities.BlackArcher);
		shootingLeftAnimation = new Animation(2, shootingLeftSprites, this);
		shootingRightAnimation = new Animation(2, shootingRightSprites, this);
		super.lives = 100;
	}
	
	public void render(Graphics g) {
		if(movingLeft && !movingRight){
			if(shotArrow){
				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					g.drawImage(shootingLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(1);
				}else{
					g.drawImage(shootingRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(2);
				}
				
			}else{
				g.drawImage(runningLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}
		}else if(movingRight && !movingLeft){
			if(shotArrow){
				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					g.drawImage(shootingLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(1);
				}else{
					g.drawImage(shootingRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(2);
				}		
			}else{
				g.drawImage(runningRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}
		}else if(getLastFacing() == 1){
			if(shotArrow){
				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					g.drawImage(shootingLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(1);
				}else{
					g.drawImage(shootingRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(2);
				}
				
			}else{
				g.drawImage(idleLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}
		}else if(getLastFacing() == 2){
			if(shotArrow){
				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					g.drawImage(shootingLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(1);
				}else{
					g.drawImage(shootingRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
					setLastFacing(2);
				}
				
			}else{
				g.drawImage(idleRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}
		}
		/*
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.RED);
		g2d.draw(bottomBounds());
		g.setColor(Color.RED);
		g2d.draw(topBounds());
		g.setColor(Color.RED);
		g2d.draw(leftBounds());
		g.setColor(Color.RED);
		g2d.draw(rightBounds());
		*/
	}
	
	private boolean onWall = false;
	public void tick(Load loadedLevel) {
		falling = true;
		
		x += velocityX;
		y += velocityY;
		
				
		
		if(movingLeft && !movingRight && !onWall){
			if(!shotArrow){
				runningLeftAnimation.runAnimation();
			}else{

				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					shootingLeftAnimation.shotAnimation();
					setLastFacing(1);
				}else{
					shootingRightAnimation.shotAnimation();
					setLastFacing(2);
				}
				
			}
		}if(movingRight && !movingLeft && !onWall){
			if(!shotArrow){
				runningRightAnimation.runAnimation();
			}else{
				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					shootingLeftAnimation.shotAnimation();
					setLastFacing(1);
				}else{
					shootingRightAnimation.shotAnimation();
					setLastFacing(2);
				}
			}
		}if(getLastFacing() == 2){
			if(!shotArrow){
				idleRightAnimation.runAnimation();
			}else{
				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					shootingLeftAnimation.shotAnimation();
					setLastFacing(1);
				}else{
					shootingRightAnimation.shotAnimation();
					setLastFacing(2);
				}
			}
		}if(getLastFacing() == 1){
			if(!shotArrow){
				idleLeftAnimation.runAnimation();
			}else{
				if(Attacks.lastRangedShot.getAngle() > Math.PI/2 || Attacks.lastRangedShot.getAngle() < (-Math.PI/2)){
					shootingLeftAnimation.shotAnimation();
					setLastFacing(1);
				}else{
					shootingRightAnimation.shotAnimation();
					setLastFacing(2);
				}
			}
		}
		
		if(falling){
			velocityY += 1.0;
		}else{
			velocityY = 0;
		}
	
		if(movingLeft && !movingRight && !shotArrow){
			velocityX = -5;
		}else if(movingRight && !movingLeft && !shotArrow){
			velocityX = 5;
		}else{
			velocityX = 0;
		}
		
		
		for(Tile tile : loadedLevel.getTiles()){
			
			if(bottomBounds().intersects(tile.topBounds)){
				y = tile.getBounds().y-62;
				falling = false;	
				jumping = false;
				velocityY = 0;	
			}
			
			if(topBounds().intersects(tile.bottomBounds)){
				jumping = false;
				falling = true;
				y = tile.y+Tile.DEFAULT_HEIGHT+1;
				velocityY = 2;

			}
			
			if(rightBounds().intersects(tile.leftBounds)){
				x -= 5;
				onWall = true;

			}else{
				onWall = false;
			}

			if(leftBounds().intersects(tile.rightBounds)){
				x += 5;
				onWall = true;

			}else{
				onWall = false;
			}
			

			
		}
		
	
	}

	
}
