package level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import tiles.Crate;
import tiles.FiveStones;
import tiles.FourSmallStones;
import tiles.FourStones;
import tiles.FourStonesGrass;
import tiles.FourThickStoneGrass;
import tiles.FourThickStones;
import tiles.HalfThiccStone;
import tiles.StoneGrass;
import tiles.ThiccStone;
import tiles.ThreeSmallStoneGrass;
import tiles.ThreeStones;
import tiles.ThreeStonesGrass;
import tiles.ThreeThickStoneGrass;
import tiles.Tile;
import tiles.TwoSmallStonesGrass;
import tiles.TwoThiccStoneGrass;
import tiles.TwoThiccStones;

public class Load {

	private BufferedImage level;
	
	private boolean isLoaded = false;
	
	private List<Tile> tiles = new ArrayList<>();
	
	private List<ArrayList<Tile>> sortedTilesByRow = new ArrayList<>();
		
	public Load(File file){
		try {
			level = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loadTiles();
		
	}
	
	
	
	private void loadTiles(){
		// Sorting by row
		for(int yPixels = 0; yPixels < level.getHeight(); yPixels++){
			sortedTilesByRow.add(yPixels, new ArrayList<Tile>());
			for(int xPixels = 0; xPixels < level.getWidth(); xPixels++){
				int pixelColor = level.getRGB(xPixels, yPixels);
				
				int red = (pixelColor >> 16) & 0xff;
				int green = (pixelColor >> 8) & 0xff;
				int blue = (pixelColor) & 0xff;
				
				//left regular grass tile
				if(red == 34 && green == 177 && blue == 76){
					tiles.add(new StoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					sortedTilesByRow.get(yPixels).add(new StoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
				
					System.out.println("1");
				}
				// mid regular grass tile
				if(red == 181 && green == 230 && blue == 29){
					tiles.add(new StoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					sortedTilesByRow.get(yPixels).add(new StoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
				
					System.out.println("2");
				}
				
				// right regular grass tile
				if(red == 255 && green == 242 && blue == 0){
					tiles.add(new StoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					sortedTilesByRow.get(yPixels).add(new StoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					
					System.out.println("3");
				}
			}
			
		}
		
		// Sorting by column
		for(int xPixels = 0; xPixels < level.getWidth(); xPixels++){
			
			for(int yPixels = 0; yPixels < level.getHeight(); yPixels++){
				int pixelColor = level.getRGB(xPixels, yPixels);
				
				int red = (pixelColor >> 16) & 0xff;
				int green = (pixelColor >> 8) & 0xff;
				int blue = (pixelColor) & 0xff;
				
				//left regular grass tile
				if(red == 0 && green == 0 && blue == 0){
					tiles.add(new StoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("1");
				}
				// mid regular grass tile
				if(red == 127 && green == 127 && blue == 127){
					tiles.add(new ThreeStones(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));

					System.out.println("2");
				}
				
				// right regular grass tile
				if(red == 136 && green == 0 && blue == 21){
					tiles.add(new FourStonesGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));

					System.out.println("3");
				}
				
				if(red == 237 && green == 28 && blue == 36){
					tiles.add(new TwoThiccStones(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("4");
					
				}
				if(red == 255 && green == 127 && blue == 39){
					tiles.add(new ThreeStonesGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("5");
					
				}
				
				if(red == 255 && green == 242 && blue == 0){
					tiles.add(new FourStones(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("6");
					
				}
				if(red == 34 && green == 177 && blue == 76){
					tiles.add(new TwoThiccStoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("7");
					
				}
				if(red == 0 && green == 162 && blue == 232){
					tiles.add(new ThiccStone(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("8");
					
				}
				
				if(red == 63 && green == 72 && blue == 204){
					tiles.add(new TwoSmallStonesGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("9");
					
				}
				if(red == 163 && green == 73 && blue == 164){
					tiles.add(new HalfThiccStone(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("10");
					
				}
				if(red == 195 && green == 195 && blue == 195){
					tiles.add(new FourThickStoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("11");
					
				}
				if(red == 185 && green ==  122 && blue == 87){
					tiles.add(new FourThickStones(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("12");
					
				}
				if(red == 255 && green ==  174 && blue == 201){
					tiles.add(new ThreeThickStoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("13");
					
				}
				if(red == 255 && green ==  201 && blue == 14){
					tiles.add(new FiveStones(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("14");
					
				}
				if(red == 239 && green ==  228 && blue == 176){
					tiles.add(new ThreeSmallStoneGrass(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("15");
					
				}
				if(red == 181 && green ==  230 && blue == 29){
					tiles.add(new FourSmallStones(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("16");
					
				}
				if(red == 153 && green ==  217 && blue == 234){
					tiles.add(new Crate(xPixels*Tile.DEFAULT_WIDTH, yPixels*Tile.DEFAULT_HEIGHT));
					System.out.println("17");
					
				}
			}
			
		}
		
		
	}
	
	public List<ArrayList<Tile>> getSortedTiles(){
		return sortedTilesByRow;
	}
	
	public List<Tile> getTiles(){
		return tiles;
	}
	
}
