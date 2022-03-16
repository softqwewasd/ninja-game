package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import attacks.Attacks;
import attacks.Ranged;
import level.Load;
import main.Animation;
import tiles.Tile;

public abstract class Bot extends Entity{
	
	private Entity player;
	
	private Entity target;

	private boolean onWall = false;

	Load loadedLevel;
	private long arrowMs;
	public List<Ranged> arrows = new ArrayList<>();
	public Ranged lastArrowShot;
	private static int DEFAULT_SPEED = 3;
	
	Random random = new Random();
	
	private Line2D lineInbetweenTargets;
	
	private int maxDistance;
	private String latestDirection;
	
	public Bot(int x, int y, Entity target, String spriteSheetPath, Load loadedLevel) {
		super(x, y, spriteSheetPath);
		this.target = target;
		// TODO Auto-generated constructor stub
		arrowMs = random.nextInt(4000)+1000;
		try {
			spriteSheet = ImageIO.read(new File("res/green_archer.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLives(3);
		lineInbetweenTargets = new Line2D.Double(new Point(x+32, y+25), new Point(target.x+32, target.y+25));
		
		maxDistance = 400;
		
		shootingLeftAnimation = new Animation(2, shootingLeftSprites, this);
		shootingRightAnimation = new Animation(2, shootingRightSprites, this);
		
	}
	
	Tile standingOnTile;
	private long initialTime = System.currentTimeMillis();
	

	private boolean targetVisible = false;
	
	private int getDistanceBetweenPoints(Point2D p1, Point2D p2){
		return (int)Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2)+Math.pow(p2.getY()-p1.getY(), 2));
	}
	private boolean hitWall = false;
	
	private List<Tile> temporaryTileList = new ArrayList<>();
	private List<Tile> temporaryTileRemovalList = new ArrayList<>();
	
	public List<Ranged> arrowsFiredByTargetToRemove = new ArrayList<>();
	
	// For a platform
	private Tile leftBorderTile;
	private Tile rightBorderTile;
	
	private boolean stoodOnLeftBorderTile = false;
	private boolean stoodOnRightBorderTile = false;
	
	
	
	public void tick(Load loadedLevel){
		runningLeftAnimation.runAnimation();
		runningRightAnimation.runAnimation();
		idleLeftAnimation.runAnimation();
		idleRightAnimation.runAnimation();
		
		falling = true;
		x += velocityX;
		y += velocityY;
		targetVisible = true;
		hitWall = false;
		
		int hitOnce = 0;
		lineInbetweenTargets.setLine(new Point(x+32, y+25), new Point(target.x+32, target.y+25));
		for(Tile tile : loadedLevel.getTiles()){
			// Check if target is visible by checking if the line between them is colliding with tile(s)
			if(lineInbetweenTargets.intersects(tile.getBounds())){
				targetVisible = false;
				
			}
			
			// Check for collisions
			if(bottomBounds().intersects(tile.topBounds)){
				y = tile.getBounds().y-62;
				falling = false;	
				jumping = false;
				velocityY = 0;	
				standingOnTile = tile;
			}
			
			if(topBounds().intersects(tile.bottomBounds)){
				jumping = false;
				falling = true;
				y = tile.y+Tile.DEFAULT_HEIGHT+1;
				velocityY = 2;

			}
			
			
		}
		if(standingOnTile != null){
			for(Tile tile : loadedLevel.getTiles()){
		
				if(tile.y == standingOnTile.y){
					// On the row
					if(!temporaryTileList.contains(tile)){
						temporaryTileList.add(tile);
					}
				}
			}
		}
		// Sort temporaryTileList
		Collections.sort(temporaryTileList, new Comparator<Tile>(){
			@Override
			public int compare(Tile t1, Tile t2){
				return Integer.compare(t1.x, t2.x);
			}
		});
		
		int i = 0;
		while(i < temporaryTileList.size()){
			if(temporaryTileList.get(i).x == standingOnTile.x){
				int k = i;
				int v = i;
				while(0 < k){
					if(k > 1){
						
						if(temporaryTileList.get(k).x == Tile.DEFAULT_WIDTH + temporaryTileList.get(k-1).x){
							k--;
						}else{
							if(temporaryTileList.get(k).x > (2*Tile.DEFAULT_WIDTH)+ temporaryTileList.get(k-1).x){
								leftBorderTile = temporaryTileList.get(k);
								break;
							}else{
								k--;
							}
						}
						
					}else{
						leftBorderTile = temporaryTileList.get(k);
						break;
					}
				}
				while(temporaryTileList.size() > v){
					if(v != temporaryTileList.size()-1){
						
						if(temporaryTileList.get(v+1).x == Tile.DEFAULT_WIDTH + temporaryTileList.get(v).x){
							v++;
							
						}else{
							if(temporaryTileList.get(v+1).x > (2*Tile.DEFAULT_WIDTH) + temporaryTileList.get(v).x){
								rightBorderTile = temporaryTileList.get(v);
								break;
							}else{
								v++;
							}
						}
					}else{
						rightBorderTile = temporaryTileList.get(temporaryTileList.size()-1);
						break;
					}

				}
				break;
			}	
			i++;
		}
		


		
		if(targetVisible && Math.hypot(getLine().getX1()-getLine().getX2(), getLine().getY1()-getLine().getY2()) < 600){

			/* Arrow shooting */ 
			// Check the difference between the current time and the time last shot was fired
			if(lastArrowShot != null){
				
				if(System.currentTimeMillis() - lastArrowShot.getMs() >= arrowMs && shotArrow == false){	
						// Arrow shot successfully 
					if(Math.atan2(target.y-y, target.x-x) > Math.PI/2 || Math.atan2(target.y-y, target.x-x) < -Math.PI/2){
						setLastFacing(1);
					}else{
						setLastFacing(2);
					}					
					arrows.add(0, new Ranged(x + 32, y + 25, Math.atan2(-target.y+y, target.x-x), System.currentTimeMillis(), loadedLevel, getEntity()));
					lastArrowShot = arrows.get(0);
					// Attack speed in milliseconds change. 1000 <= arrowMs <= 1500
					arrowMs = random.nextInt(500)+1000;
					System.out.println(Math.atan2(-target.y+y, target.x-x));
					// Set new time on the last shot
					initialTime = System.currentTimeMillis();
					
					shotArrow = true;
							
				}
					

				
			}else{
				System.out.println(Math.sqrt(Math.hypot(getLine().getX1()-getLine().getX2(), getLine().getY1()-getLine().getY2())));
				arrows.add(0, new Ranged(x + 32, y + 25, Math.atan2(-target.y+y, target.x-x), System.currentTimeMillis(), loadedLevel, getEntity()));
				lastArrowShot = arrows.get(0);
				arrowMs = random.nextInt(500)+1000;
					
				// Set new time on the last shot
				initialTime = System.currentTimeMillis();
				shotArrow = true;
				if(Math.atan2(target.y-y, target.x-x) > Math.PI/2 || Math.atan2(target.y-y, target.x-x) < -Math.PI/2){
					setLastFacing(1);
				}else{
					setLastFacing(2);
				}
			}
			
			if(velocityX == DEFAULT_SPEED){
				setLastFacing(2);
			}else if(velocityX == -DEFAULT_SPEED){
				setLastFacing(1);
			}
			
			velocityX = 0;
			
			
		}else{
						
			for(Tile tile : loadedLevel.getTiles()){
				
				if(rightBounds().intersects(tile.leftBounds)){
					x -= DEFAULT_SPEED;
					// Changing direction
					setLastFacing(1);
		
				}else if(leftBounds().intersects(tile.rightBounds)){
					x += DEFAULT_SPEED;
					setLastFacing(2);
	
					
				}

			}
			
			
			if(standingOnTile != null && (leftBorderTile != null || rightBorderTile != null)){
				if(standingOnTile.x == leftBorderTile.x){
					stoodOnLeftBorderTile = true;
					stoodOnRightBorderTile = false;
				}else if(standingOnTile.x == rightBorderTile.x){
					stoodOnLeftBorderTile = false;
					stoodOnRightBorderTile = true;
				}else{
					stoodOnLeftBorderTile = false;
					stoodOnRightBorderTile = false;
				}
				
			}
			
			if(!shotArrow){
				if(getLastFacing() == 2){
					velocityX = DEFAULT_SPEED;
				}else if(getLastFacing() == 1){
					velocityX = -DEFAULT_SPEED;
				}else{
					velocityX = DEFAULT_SPEED;
				}
				
				if(stoodOnLeftBorderTile && !stoodOnRightBorderTile){
					velocityX = DEFAULT_SPEED;
					setLastFacing(2);
				}else if(stoodOnRightBorderTile && !stoodOnLeftBorderTile){
					velocityX = -DEFAULT_SPEED;
					setLastFacing(1);
				}
				
			}else{
				velocityX = 0;
			}
			
			

			
			if(standingOnTile == null){
				velocityX = velocityX * -1;
			}
			
			
		}
		

		
	
		// Update the arrows
		for(Ranged r : arrows){
			r.tick();
		}
		
		if(arrows.size() > 10){
			arrows.remove(10);
		}
		// Start animations
		
		
			
		if(lastArrowShot != null){
			if(shotArrow){
				if(getLastFacing() == 2){
					shootingRightAnimation.shotAnimation();

				}else if(getLastFacing() == 1){
					shootingLeftAnimation.shotAnimation();
					
				}	
			}
		}
		

		if(falling){
			velocityY += 1;
		}
		
		if(velocityY < 0){
			jumping = false;
		}
		

		
		if(lives <= 0){
			isDead = true;
		}
		
	}
	
	@Override
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;

		for(Ranged r : arrows){
			r.render(g);
		}

		/*
		g.setColor(Color.YELLOW);
		g2d.draw(lineInbetweenTargets);
		*/
		
		if(shotArrow){
			if(getLastFacing() == 2){
				g.drawImage(shootingRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}else if(getLastFacing() == 1){
				g.drawImage(shootingLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}
		}else{
			if(velocityX == -DEFAULT_SPEED){
				g.drawImage(runningLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}else if(velocityX == DEFAULT_SPEED){
				g.drawImage(runningRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
			}else if(velocityX == 0){
				if(getLastFacing() == 1){
					g.drawImage(idleLeftAnimation.getCurrentSprite(), x, y, 64, 64, null);
				}else if(getLastFacing() == 2){
					g.drawImage(idleRightAnimation.getCurrentSprite(), x, y, 64, 64, null);
				}
			}
		}
		
	}
	
	
	public List<Ranged> getArrows(){
		return arrows;
	}
	public Line2D getLine(){
		return lineInbetweenTargets;
	}
	
}
