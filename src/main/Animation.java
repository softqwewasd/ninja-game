package main;

import java.awt.image.BufferedImage;
import java.util.List;

import attacks.Spell;
import entities.Boss;
import entities.Entity;

public class Animation {
	
	private int speed;
	
	private int index = 0;
	private int count = 0;
	
	private int repeat;
	
	private boolean finished = false;
	
	private List<BufferedImage> sprites;
	private BufferedImage currentTempSprite;
	
	private Entity entity;
	
	public Animation(int speed, List<BufferedImage> sprites, Entity entity){
		this.speed = speed;
		this.sprites = sprites;
		this.entity = entity;
	}
	
	
	public void runAnimation(){
		index++;
		
		if(index > speed){
			index = 0;
			
			for(int i = 0; i < sprites.size(); i++){
				if(count == i){
					currentTempSprite = sprites.get(i);
				}
			}
			count++;
			
			if(count > sprites.size()){
				count = 0;
			}
			
		}
			
		
	}
	
	public void shotAnimation(){
		index++;
		
		if(index > speed && entity.shotArrow){
			index = 0;
			
			for(int i = 0; i < sprites.size(); i++){
				if(count == i){
					currentTempSprite = sprites.get(i);
				}
			}
			count++;
			
			if(count > sprites.size()){
				count = 0;
		
				entity.shotArrow = false;
			}
			
		}
			
	}
	
	public void shotAnimation(Spell spell, Boss boss){
		index++;
		
		if(index > speed){
			index = 0;
			
			for(int i = 0; i < sprites.size(); i++){
				if(count == i){
					currentTempSprite = sprites.get(i);
				}
			}
			count++;
			
			if(count > sprites.size()){
				count = 0;
				if(spell.usingAngle){
					boss.usedTargetShot = false;	
				}else{
					boss.usedMapShot = false;
				}
				
				
			}
			
		}
			
	}
	public BufferedImage getCurrentSprite(){
		return currentTempSprite;
	}
	
	
}
