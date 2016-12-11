package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Grid;
import game.Player;
import gui.MainWindow;

public class GameLoop {
	
	private MainWindow mainWindow;
	
	private long timer = 0;
	private long tickInterval = 10;
	
	private long renderTimer = 0;
	private int framesPerSecond = 60;
	
	private Grid grid = new Grid();
	private Player player = new Player();
	
	private ArrayList<Integer> keysPressed = new ArrayList<Integer>();

	public GameLoop(MainWindow mainWindow) {
		
		this.mainWindow = mainWindow;
		
		setupKeyHandler();
		init();
		
	}
	
	private void setupKeyHandler()
	{
		this.mainWindow.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int index = keysPressed.indexOf(e.getKeyCode());
				if (index >= 0 && index <= keysPressed.size()-1) {
					keysPressed.remove(index);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (!keysPressed.contains(e.getKeyCode())) {
					keysPressed.add(e.getKeyCode());
				}
			}
		});
	}
	
	public void init()
	{
		// Init grid
		grid.init();
		
		// Reposition player
		player.reposition(grid);
		
		// Set up buttons
		grid.setupButtons();
	}

	public void start()
	{
		long lastTime = System.currentTimeMillis();
		long currentTime;
		long elapsedTime;

		
		while(true)
		{
			currentTime = System.currentTimeMillis();
			elapsedTime = currentTime - lastTime;
			
			handleInput();
			update(elapsedTime);
			render(elapsedTime);
			
			lastTime = currentTime;
		}
	}
	
	private void handleInput()
	{		
		player.handleInput(keysPressed);
	}
	
	private void update(long elapsedTime)
	{
		timer += elapsedTime;
		
		if (timer<tickInterval)
		{
			return;
		}
		
		timer = 0;
		
		if (Main.computerWindow.isVisible()) {
			return;
		}
		
		// Update grid
		grid.update();
		
		// Update player
		player.update(grid);
		
	}
	
	private void render(long elapsedTime)
	{
		renderTimer += elapsedTime;
		
		// Only render a fixed number of frames per second
		if (renderTimer <= 1000 / framesPerSecond )
		{
			return;
		}
		
		renderTimer = 0;
		
		// Create render image
		BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		
		// Set background colour
		g2d.setColor(Color.darkGray);
		g2d.fillRect(0, 0, 800, 800);
		
		if (Main.computerWindow.isVisible()) {
			
			g2d.setColor(Color.white);
			g2d.drawString("You must close the computer window to continue.", 240, 280);
			
		} else {
		
			// Draw grid
			grid.render(g2d);
			
			// Draw player
			player.render(g2d, grid.cellSize);
		}
				
		// Repaint with new render image
		mainWindow.drawPane.image = image;
		mainWindow.repaint();

	}
}
