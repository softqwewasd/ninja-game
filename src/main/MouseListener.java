package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import attacks.Attacks;
import attacks.Ranged;
import entities.Entities;
import entities.Entity;
import level.Load;

public class MouseListener extends MouseAdapter{
	
	private Entity entity;
	private Load load;
	private Game game;
	
	public MouseListener(Entity entity, Load load, Game game){
		this.entity = entity;
		this.load = load;
		this.game = game;
	}
	
	
	public void mouseReleased(MouseEvent e){
		if(entity.isDead && game.started){
			if(Game.restartButton.get().contains(e.getPoint())){
				System.out.println(e.getClickCount());
				game.retry();
			}
			if(Game.quitButton.get().contains(e.getPoint())){
				System.exit(0);
			}
		}else if(!entity.isDead && !game.started){
			if(Game.startButton.get().contains(e.getPoint())){
				game.started = true;
			}
			if(Game.quitButton.get().contains(e.getPoint())){
				System.exit(0);
			}
		}else if(game.hasWon){
			if(Game.quitButton.get().contains(e.getPoint())){
				System.exit(0);
			}
		}else if(!entity.isDead && game.started){
			if(Attacks.ranged.size() > 0){
				if(System.currentTimeMillis() - Attacks.lastRangedShot.getMs() > 400){
					double x;
					if(entity.x >= 9984){
						x = (9984 +e.getX()) - (entity.x+32);
	
						System.out.println("x: " + x);
					}else{
						x = e.getX() - Window.WIDTH/2;
						System.out.println("x: " + x);
					}
					double y = entity.y - e.getY()+25;
					System.out.println(Math.atan2(y, x));
					System.out.println(entity.y + " - " + e.getY());
					Attacks.ranged.add(0, new Ranged(entity.x + 32, entity.y + 25, Math.atan2(y, x), System.currentTimeMillis(), load, Entities.BlackArcher));
					Attacks.lastRangedShot = Attacks.ranged.get(0);
					entity.shotArrow = true;
				}
			}else{
				if(Attacks.lastRangedShot != null){
					if(System.currentTimeMillis()-Attacks.lastRangedShot.getMs() > 400){
	
						System.out.println(entity.y + " - " + e.getY());
						System.out.println("lol no");
						double x = e.getX() - Window.WIDTH/2;
						double y = entity.y - e.getY()+32;
						System.out.println(Math.atan2(y, x));
						
						Attacks.ranged.add(0, new Ranged(entity.x + 32, entity.y + 25, Math.atan2(y, x), System.currentTimeMillis(), load, Entities.BlackArcher));
						Attacks.lastRangedShot = Attacks.ranged.get(0);
						entity.shotArrow = true;
					}
				}else{
				System.out.println("lol no");
					double x = e.getX() - Window.WIDTH/2;
					double y = entity.y - e.getY()+32;
					System.out.println(Math.atan2(y, x));
	
					System.out.println(entity.y + " - " + e.getY());
					Attacks.ranged.add(0, new Ranged(entity.x + 32, entity.y + 25, Math.atan2(y, x), System.currentTimeMillis(), load, Entities.BlackArcher));
					Attacks.lastRangedShot = Attacks.ranged.get(0);
					entity.shotArrow = true;
				}
			}
		}
	}
	
}
