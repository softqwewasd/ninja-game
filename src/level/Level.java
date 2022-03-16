package level;

import java.awt.Graphics;

import tiles.Tile;

public class Level {

	private Load loadedLevel;
	
	public Level(Load loadedLevel){
		this.loadedLevel = loadedLevel;
	}
	
	public void render(Graphics g){
		for(Tile tile : loadedLevel.getTiles()){
			tile.render(g);
		}
	}
	
	public Load getLoadedLevel(){
		return loadedLevel;
	}
	
	private void sortTiles(){
		
	}
	
}
