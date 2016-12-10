package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.Grid;
import game.Player;
import gui.MainWindow;

public class GameLoop {
	
	private MainWindow mainWindow;
	
	private long timer = 0;
	private long tickInterval = 0;
	
	private long renderTimer = 0;
	private int framesPerSecond = 60;
	
	private Grid grid = new Grid();
	private Player player = new Player();

	public GameLoop(MainWindow mainWindow) {
		
		this.mainWindow = mainWindow;
		
		init();
		
	}
	
	public void init()
	{
		Random random = new Random();
		
		player.x = grid.xOffset + ((grid.padding+grid.cellSize)*random.nextInt(grid.width));
		player.y = grid.yOffset + ((grid.padding+grid.cellSize)*random.nextInt(grid.height));
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
			
			update(elapsedTime);
			render(elapsedTime);
			
			lastTime = currentTime;
		}
	}
	
	private void update(long elapsedTime)
	{
		timer += elapsedTime;
		
		if (timer<tickInterval)
		{
			return;
		}
		
		timer = 0;
		
		// TODO: Add update logic here
		
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
		
		// Draw grid
		grid.render(g2d);
		
		// Draw player
		player.render(g2d, grid.cellSize);
				
		// Repaint with new render image
		mainWindow.drawPane.image = image;
		mainWindow.repaint();

	}
}
