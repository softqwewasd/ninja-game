package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import attacks.Attacks;
import attacks.Ranged;
import attacks.Spell;
import entities.BlackArcher;
import entities.BlueArcher;
import entities.Boss;
import entities.Bot;
import entities.Entity;
import entities.GreenArcher;
import entities.YellowArcher;
import level.Load;
import tiles.FiveStones;
import tiles.Tile;
import level.Level;
public class Game extends Canvas implements Runnable{
	
	public boolean running = false;
	
	private Thread mainThread;
	
	private Window window;
	
	private BufferedImage bg;
	
	Load levelLoader;
	Level testLevel;
	
	Entity testPlayer;

	GreenArcher enemy;
	
	private Attacks attacks;
	
	public boolean started = false;
	
	public List<Bot> enemies = new ArrayList<>();
	private List<Bot> enemiesToRemove = new ArrayList<>();
	Boss bauws;
	
	private List<Ranged> arrowsFiredByTargetAtBossToRemove = new ArrayList<>();
	
	public static boolean lockedScreen = false;
	public static Line2D bossEntrance;
	
	
	public static LmaoButton restartButton = new LmaoButton(300, 200, "Restart");
	public static LmaoButton quitButton = new LmaoButton(300, 280, "Quit");
	public static LmaoButton startButton = new LmaoButton(300, 200, "Start");
	
	public boolean hasWon = false;
	
	public static void main(String args[]){
		new Game();
	}
	
	public Game(){
		
		try {
			bg = ImageIO.read(new File("res/main-tileset/JnRLayer01.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		levelLoader = new Load(new File("res/testlevel.png"));
		testLevel = new Level(levelLoader);
		testPlayer = new BlackArcher(110, 320);
		
		enemies.add(new GreenArcher(78, 98, testPlayer, levelLoader));
		enemies.add(new BlueArcher(543, 386, testPlayer, levelLoader));
		enemies.add(new GreenArcher(893, 322, testPlayer, levelLoader));
		enemies.add(new YellowArcher(1337, 162, testPlayer, levelLoader));
		enemies.add(new GreenArcher(1843, 322, testPlayer, levelLoader));
		enemies.add(new GreenArcher(2213, 322, testPlayer, levelLoader));
		enemies.add(new BlueArcher(1648, 322, testPlayer, levelLoader));
		enemies.add(new GreenArcher(1948, 162, testPlayer, levelLoader));
		enemies.add(new YellowArcher(1948, 32, testPlayer, levelLoader));
		enemies.add(new YellowArcher(2223, 32, testPlayer, levelLoader));
		enemies.add(new YellowArcher(2608, 130, testPlayer, levelLoader));
		enemies.add(new BlueArcher(2953, 130, testPlayer, levelLoader));
		enemies.add(new BlueArcher(3388, 290, testPlayer, levelLoader));
		enemies.add(new BlueArcher(3693, 226, testPlayer, levelLoader));
		enemies.add(new YellowArcher(4033, 322, testPlayer, levelLoader));
		enemies.add(new YellowArcher(4483, 322, testPlayer, levelLoader));
		enemies.add(new YellowArcher(4863, 322, testPlayer, levelLoader));
		enemies.add(new BlueArcher(5303, 322, testPlayer, levelLoader));
		enemies.add(new GreenArcher(6683, 258, testPlayer, levelLoader));
		enemies.add(new GreenArcher(7358, 354, testPlayer, levelLoader));
		enemies.add(new GreenArcher(8178, 258, testPlayer, levelLoader));
		enemies.add(new YellowArcher(8443, 258, testPlayer, levelLoader));
		enemies.add(new YellowArcher(7358, 354, testPlayer, levelLoader));
		enemies.add(new BlueArcher(8708, 258, testPlayer, levelLoader));
		enemies.add(new GreenArcher(8848, 64, testPlayer, levelLoader));
		enemies.add(new GreenArcher(8913, 226, testPlayer, levelLoader));
		enemies.add(new GreenArcher(9908, 194, testPlayer, levelLoader));
		
		bossEntrance = new Line2D.Double(new Point(313*32, 0), new Point(313*32, 800));
		
		attacks = new Attacks();
		bauws = new Boss(10540, 220, testPlayer);
		window = new Window(this);
		
	}
	
	
	public void run() {
		
		this.requestFocus();
		this.addKeyListener(new KeyListener(testPlayer));
		this.addMouseListener(new MouseListener(testPlayer, levelLoader, this));
		
		
		long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
           
            delta += (now - lastTime) / ns;
            
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running)
                render();
                frames++;
            if(System.currentTimeMillis() - timer > 1000){
            	timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
           
        }
        end();
	}
	
	
	public void start(){
		mainThread = new Thread(this);
		running = true;
		mainThread.start();
		System.out.println("mainThread started");
	}
	
	public void end(){
		try {
			mainThread.join();
			running = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void retry(){
		testPlayer.x = 110;
		testPlayer.y = 320;

		enemies.clear();
		enemies.add(new GreenArcher(78, 98, testPlayer, levelLoader));
		enemies.add(new BlueArcher(543, 386, testPlayer, levelLoader));
		enemies.add(new GreenArcher(893, 322, testPlayer, levelLoader));
		enemies.add(new YellowArcher(1337, 162, testPlayer, levelLoader));
		enemies.add(new GreenArcher(1843, 322, testPlayer, levelLoader));
		enemies.add(new GreenArcher(2213, 322, testPlayer, levelLoader));
		enemies.add(new BlueArcher(1648, 322, testPlayer, levelLoader));
		enemies.add(new GreenArcher(1948, 162, testPlayer, levelLoader));
		enemies.add(new YellowArcher(1948, 32, testPlayer, levelLoader));
		enemies.add(new YellowArcher(2223, 32, testPlayer, levelLoader));
		enemies.add(new YellowArcher(2608, 130, testPlayer, levelLoader));
		enemies.add(new BlueArcher(2953, 130, testPlayer, levelLoader));
		enemies.add(new BlueArcher(3388, 290, testPlayer, levelLoader));
		enemies.add(new BlueArcher(3693, 226, testPlayer, levelLoader));
		enemies.add(new YellowArcher(4033, 322, testPlayer, levelLoader));
		enemies.add(new YellowArcher(4483, 322, testPlayer, levelLoader));
		enemies.add(new YellowArcher(4863, 322, testPlayer, levelLoader));
		enemies.add(new BlueArcher(5303, 322, testPlayer, levelLoader));
		enemies.add(new GreenArcher(6683, 258, testPlayer, levelLoader));
		enemies.add(new GreenArcher(7358, 354, testPlayer, levelLoader));
		enemies.add(new GreenArcher(8178, 258, testPlayer, levelLoader));
		enemies.add(new YellowArcher(8443, 258, testPlayer, levelLoader));
		enemies.add(new YellowArcher(7358, 354, testPlayer, levelLoader));
		enemies.add(new BlueArcher(8708, 258, testPlayer, levelLoader));
		enemies.add(new GreenArcher(8848, 64, testPlayer, levelLoader));
		enemies.add(new GreenArcher(8913, 226, testPlayer, levelLoader));
		enemies.add(new GreenArcher(9908, 194, testPlayer, levelLoader));
		Attacks.ranged.clear();
		Attacks.spells.clear();
		
		bauws.lives = 15;
		bauws.isDead = false;

		testPlayer.isDead = false;
		testPlayer.lives = 100;
		
	}
	
	private List<Ranged> arrowsFiredByBotToRemove = new ArrayList<>();
	private void tick(){
		if(started && !testPlayer.isDead && !hasWon){
			for(Bot bot : enemies){
				
				bot.tick(levelLoader);
				
				for(Ranged r : new ArrayList<Ranged>(Attacks.ranged)){
					if(r.getBounds().intersects(bot.bottomBounds()) || r.getBounds().intersects(bot.topBounds()) || r.getBounds().intersects(bot.leftBounds()) || r.getBounds().intersects(bot.rightBounds()) && r.inMotion){
						
						if(r.inMotion){
	
							bot.arrowsFiredByTargetToRemove.add(r);
							bot.lives--;	
						}
						
					}
				}
	
				if(bot.lives <= 0){
					bot.isDead = true;
				}
				
				for(Ranged r : new ArrayList<Ranged>(bot.getArrows())){
					if(r.getBounds().intersects(testPlayer.leftBounds()) || r.getBounds().intersects(testPlayer.rightBounds()) || r.getBounds().intersects(testPlayer.topBounds()) || r.getBounds().intersects(testPlayer.bottomBounds())){
						if(r.inMotion){
							arrowsFiredByBotToRemove.add(r);
							testPlayer.lives--;	
						}
						
					}
				}
				
				if(testPlayer.lives <= 0){
					testPlayer.isDead = true;
				}
				
				
				
				bot.arrows.removeAll(arrowsFiredByBotToRemove);
				arrowsFiredByBotToRemove.clear();
				
	
				Attacks.ranged.removeAll(bot.arrowsFiredByTargetToRemove);
				bot.arrowsFiredByTargetToRemove.clear();
				
				
				
				if(bot.isDead){
					enemiesToRemove.add(bot);
				}
				
				
				
			}
			if(testPlayer.y > 700){
				testPlayer.x = 110;
				testPlayer.y = 320;
				testPlayer.lives--;
			}
			for(Ranged r : new ArrayList<Ranged>(Attacks.ranged)){
				if(r.getBounds().intersects(bauws.getBounds()) || bauws.getBounds().intersectsLine(r.getBounds())){
					if(r.inMotion){
						arrowsFiredByTargetAtBossToRemove.add(r);
						bauws.lives--;
						
						
					}
				}
			}
			if(bauws.lives <= 0){
				bauws.isDead = true;
				hasWon = true;
			}
	
	
			attacks.tick(testPlayer, levelLoader);
			testPlayer.tick(levelLoader);
			Attacks.ranged.removeAll(arrowsFiredByTargetAtBossToRemove);
			arrowsFiredByTargetAtBossToRemove.clear();
			if(testPlayer.rightBounds().intersects(bauws.getZone())){
					
				testPlayer.x -= 5;
			}	
			
			
			enemies.removeAll(enemiesToRemove);
			enemiesToRemove.clear();
			
			
			for(Spell spell : Attacks.spells){
				
				
				if(spell.velocityX == 0 && spell.velocityY == 0){
					spellsToRemove.add(spell);
					
				}else if(spell.getBounds().intersects(testPlayer.bottomBounds()) || spell.getBounds().intersects(testPlayer.leftBounds()) || spell.getBounds().intersects(testPlayer.rightBounds()) || spell.getBounds().intersects(testPlayer.topBounds())){
					spellsToRemove.add(spell);
					testPlayer.lives--;
				}
				
				
			}
			Attacks.spells.removeAll(spellsToRemove);
			spellsToRemove.clear();
	
	
			bauws.tick(levelLoader);
			
			if(testPlayer.x > (313*32)){
				lockedScreen = true;
			}
			
			if(lockedScreen){
				if(testPlayer.leftBounds().intersectsLine(bossEntrance)){
					testPlayer.x += 5;
				}
			}
		}
		//System.out.println(testPlayer.x + "x" + testPlayer.y);
	}
	
	int x = 0;
	List<Spell> spellsToRemove = new ArrayList<>();
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(bg, 0, 0, 800, 600, null);
		
		if(!testPlayer.isDead && started && !hasWon){

			g.setFont(new Font("Arial", Font.PLAIN, 32));
			g.drawString("Lives: " + testPlayer.lives, 30, 30);
			
			if(testPlayer.x < 9984){
				g.translate(-testPlayer.x-32 + Window.WIDTH/2, 0);	
			}else{
				g.translate(-9984, 0);
				lockedScreen = true;
			}
			
			testLevel.render(g);
			
			g.setColor(Color.GREEN);
			g2d.draw(bossEntrance);
			
			
			for(Bot bot : enemies){
				bot.render(g);
				
			}
			testPlayer.render(g);
			
			attacks.render(g);
			bauws.render(g);
			
			
			if(testPlayer.x < 9984){
				g.translate(testPlayer.x-32 + Window.WIDTH/2, 0);
			}else{
				g.translate(9984, 0);
			}
		}else if(!started && !testPlayer.isDead && !hasWon){
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", Font.PLAIN, 32));
			g.drawString("Coolgame", 330, 130);
			
			startButton.render(g2d);
			quitButton.render(g2d);
		}else if(testPlayer.isDead && started && !hasWon){
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.PLAIN, 32));
			g.drawString("YOU DIED", 320, 130);
			
			restartButton.render(g2d);
			quitButton.render(g2d);
		}else if(hasWon && !testPlayer.isDead){
			
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", Font.PLAIN, 32));
			g.drawString("YOU WON!", 320, 130);
			
			
			quitButton.render(g2d);
			
		}
		g.dispose();
		bs.show();
		
		
	}
	
	
}
