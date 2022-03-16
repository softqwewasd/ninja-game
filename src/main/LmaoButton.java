package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class LmaoButton{

	final public static int WIDTH = 200;
	final public static int HEIGHT = 60;
	
	private Rectangle button;
	private int x, y;
	private String text;
	
	FontMetrics textMetrics;
	
	public LmaoButton(int x, int y, String text){
		this.x = x;
		this.y = y;
		button = new Rectangle(x, y, WIDTH, HEIGHT);
		this.text = text;
		
	}
	
	public void render(Graphics2D g){
		Font font = new Font("Arial", Font.PLAIN, 20);
		textMetrics = g.getFontMetrics(font);
		g.setFont(font);
		
		g.setColor(new Color(51, 118, 142));
		g.fill(button);
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, WIDTH, HEIGHT);
		
		
		g.setColor(Color.WHITE);
		g.drawString(text, x + ((WIDTH - textMetrics.stringWidth(text))/2), y + ((HEIGHT - textMetrics.getHeight())/2) + textMetrics.getAscent());

		
	}
	
	public Rectangle get(){
		return button;
	}
	
}
