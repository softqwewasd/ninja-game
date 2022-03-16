package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import entities.Entity;

public class KeyListener extends KeyAdapter{
	
	private Entity entity;
	
	public KeyListener(Entity entity){
		this.entity = entity;
	}
	
	public void keyPressed(KeyEvent e){
		
		if(e.getKeyCode() == KeyEvent.VK_A){
			entity.movingLeft = true;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			entity.movingRight = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_W && !entity.jumping && !entity.falling){
			entity.jumping = true;
			entity.velocityY = -18;
		}

		
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_A){
			entity.movingLeft = false;
			entity.setLastFacing(1);
			
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			entity.movingRight = false;
			entity.setLastFacing(2);
		}
		
	}
	
}
