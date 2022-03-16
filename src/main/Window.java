package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	final public static int WIDTH = 800;
	final public static int HEIGHT = 600;
	
	final public static String TITLE = ":)";
	
	private JFrame window;
	private Dimension windowDimension;
	
	public Window(Game game){
		windowDimension = new Dimension(WIDTH, HEIGHT);
		window = new JFrame(TITLE);
		
		// screen size init
		window.setPreferredSize(windowDimension);
		window.setMinimumSize(windowDimension);
		window.setMaximumSize(windowDimension);
		window.setResizable(false);
		
		// window location centered
		window.setLocationRelativeTo(null);
		
		// exit all activities when window closed
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.add(game);
		
		// show window
		window.setVisible(true);
		
		
		game.start();
	}
}
