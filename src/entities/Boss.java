package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import attacks.Attacks;
import attacks.Ranged;
import attacks.Spell;
import level.Load;
import main.Animation;
import main.Window;
import tiles.Tile;

public class Boss {
	
	// Boss will not extend the abstract class Entity even though it is in the entites package
	// This is because the boss will have almost completely different properties than the regular entities
	
	// The boss is static and therefore will not have a speed nor proper collision bounds other than a simple-
	// rectangle for the arrows to hit
	
	final public static int WIDTH = 196;
	final public static int HEIGHT = 196;
	
	public int x;
	public int y;
	
	private BufferedImage spriteSheet, effectsSpriteSheet, mapShotSprite, targetShotSprite;
	
	
	private List<BufferedImage> idleSprites = new ArrayList<>();
	private List<BufferedImage> mapShotSprites = new ArrayList<>();
	private List<BufferedImage> targetShotSprites = new ArrayList<>();
	
	
	private Animation idleAnimation;
	private Animation mapShotAnimation;
	private Animation targetShotAnimation;
	
	private Line2D lineBetweenBossAndTarget;
	
	private boolean isTargetVisible = false;
	public boolean usedTargetShot = false;
	public boolean usedMapShot = false;
	
	public Spell lastSpell = null;
	
	private Entity target;
	
	private double angle;
	
	private long differenceInTime;
	
	// Because the coordinates will not change, therefore creating a new object when checking is redundant
	private Rectangle zone;
	private Rectangle bounds;
	
	Random r = new Random();
	
	public int lives = 15;
	
	public boolean isDead = false;
	
	public Boss(int x, int y, Entity target){
		this.x = x;
		this.y = y;
		
		try {
			spriteSheet = ImageIO.read(new File("res/boss.gif"));
			effectsSpriteSheet = ImageIO.read(new File("res/effects.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapShotSprite = effectsSpriteSheet.getSubimage(0, 0, 21, 20);	
		targetShotSprite = effectsSpriteSheet.getSubimage(21, 0, 18, 18);
		
		this.target = target;
		idleSprites.add(spriteSheet.getSubimage(159, 1, 74, 47));
		idleSprites.add(spriteSheet.getSubimage(159, 49, 75, 47));
		idleSprites.add(spriteSheet.getSubimage(159, 98, 72, 46));
		
		mapShotSprites.add(spriteSheet.getSubimage(163, 199, 73, 37));
		mapShotSprites.add(spriteSheet.getSubimage(159, 237, 74, 51));
		mapShotSprites.add(spriteSheet.getSubimage(157, 291, 79, 53));
		
		targetShotSprites.add(spriteSheet.getSubimage(75, 186, 81, 37));
		targetShotSprites.add(spriteSheet.getSubimage(70, 226, 85, 38));
		targetShotSprites.add(spriteSheet.getSubimage(68, 267, 87, 37));
		targetShotSprites.add(spriteSheet.getSubimage(81, 305, 67, 43));
		
		idleAnimation = new Animation(7, idleSprites, null);
		mapShotAnimation = new Animation(4, mapShotSprites, null);
		targetShotAnimation = new Animation(4, targetShotSprites, null);
		
		bounds = new Rectangle(x, y+HEIGHT/3, WIDTH, 2*HEIGHT/3);
		zone = new Rectangle(x-20, 0, WIDTH+40, Window.HEIGHT-128);
		
		differenceInTime = r.nextInt(1000)+500;
		
		lineBetweenBossAndTarget = new Line2D.Double(x+20, y+(3*HEIGHT)/4, target.x+32, target.y+25);
		
	}
	
	
	public Rectangle getBounds(){
		return bounds;
	}

	
	public Rectangle getZone(){
		return zone;
	}
	
	public void render(Graphics g){
		if(usedTargetShot && !usedMapShot){
			g.drawImage(targetShotAnimation.getCurrentSprite(), x, y, WIDTH, HEIGHT, null);
		}else if(usedMapShot && ! usedTargetShot){
			g.drawImage(mapShotAnimation.getCurrentSprite(), x, y, WIDTH, HEIGHT, null);
		}else{
			g.drawImage(idleAnimation.getCurrentSprite(), x, y, WIDTH, HEIGHT, null);
		}
		
		Graphics2D g2d = (Graphics2D) g;
		//g.setColor(Color.BLUE);
		//g2d.draw(getBounds());
		
		//g.setColor(Color.GREEN);
		//g2d.draw(getZone());
		/*
		g.setColor(Color.YELLOW);
		g2d.draw(lineBetweenBossAndTarget);
		*/
		
		
	}
	
	
	
	public void tick(Load loadedLevel){
		
		// Update line
		lineBetweenBossAndTarget.setLine(x+20, y+(3*HEIGHT)/4, target.x+32, target.y+25);
		angle = Math.atan2((y+(3*HEIGHT)/4)-(target.y+25), (target.x+32)-(x+20));
		
		for(Tile tile : loadedLevel.getTiles()){
			if(lineBetweenBossAndTarget.intersects(tile.getBounds())){
				isTargetVisible = false;
				break;
			}else{
				isTargetVisible = true;
				
			}
			
		}

		
		if(isTargetVisible){
			
			if(Math.hypot(lineBetweenBossAndTarget.getX1()-lineBetweenBossAndTarget.getX2(), lineBetweenBossAndTarget.getY1()-lineBetweenBossAndTarget.getY2()) < 600){
				if(lastSpell != null){
					if(System.currentTimeMillis() - lastSpell.lastTimeShot > differenceInTime){
						
						Attacks.spells.add(new Spell(x+10, (y+(3*HEIGHT)/4)-40, targetShotSprite, angle));
						differenceInTime = r.nextInt(1500)+1000;
						lastSpell = Attacks.spells.get(Attacks.spells.size()-1);
						usedTargetShot = true;
						usedMapShot = false;

						System.out.println("wtf");
					}
					
				}else{
					Attacks.spells.add(new Spell(x+10, (y+(3*HEIGHT)/4)-40, targetShotSprite, angle));
					differenceInTime = r.nextInt(1500)+1000;
					lastSpell = Attacks.spells.get(Attacks.spells.size()-1);
					usedTargetShot = true;
					usedMapShot = false;
					System.out.println("??");
				}
				
			}
			
			
		}else{
			if(Math.hypot(lineBetweenBossAndTarget.getX1()-lineBetweenBossAndTarget.getX2(), lineBetweenBossAndTarget.getY1()-lineBetweenBossAndTarget.getY2()) < 600){
				if(lastSpell != null){
					if(System.currentTimeMillis() - lastSpell.lastTimeShot > differenceInTime){
						for(int x = 0; x < 10; x++){
							Attacks.spells.add(new Spell(this.x+10, (y+(3*HEIGHT)/4)-40, mapShotSprite, (-2*x)-5, (-3*x)-10));
							
						}
						lastSpell = Attacks.spells.get(Attacks.spells.size()-1);
						differenceInTime = r.nextInt(2200)+1700;
						usedTargetShot = false;
						usedMapShot = true;

						System.out.println("vad nuasd");
					}
				}else{
					for(int x = 0; x < 10; x++){
						Attacks.spells.add(new Spell(this.x+10, (y+(3*HEIGHT)/4)-40, mapShotSprite, (-2*x)-5, (-3*x)-10));
						
					}
					lastSpell = Attacks.spells.get(Attacks.spells.size()-1);
					differenceInTime = r.nextInt(2200)+1700;
					usedTargetShot = false;
					usedMapShot = true;
					System.out.println("vad nu");
				}

			}
		}
		
		
		idleAnimation.runAnimation();
		if(usedTargetShot && !usedMapShot){
			targetShotAnimation.shotAnimation(lastSpell, this);
		}else if(usedMapShot && ! usedTargetShot){
			mapShotAnimation.shotAnimation(lastSpell, this);
		}
		
	}
	
	public boolean isTargetVisible(){
		return isTargetVisible;
	}
	
}
