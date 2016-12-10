package game;

import game.gridObjects.EmptyCell;
import game.gridObjects.Player;

import java.awt.Graphics2D;
import java.util.Random;

public class Grid {
	
	private GridObject[][] multi = null;
	
	private int width = 13;
	private int height = 13;
	
	private int cellSize = 40;
	private int padding = 1;
	
	private int xOffset = 30;
	private int yOffset = 20;
	
	private Player player = new Player();
	
	public Grid()
	{
		multi = new GridObject[width][height];
		
		for (int x = 0; x < multi.length; x++) {
			for (int y = 0; y < multi[x].length; y++) {
				multi[x][y] = new EmptyCell();
			}
		}
		
		setup();
	}
	
	public void setup()
	{
		Random random = new Random();
		int playerX = random.nextInt(width);
		int playerY = random.nextInt(height);
		
		multi[playerX][playerY] = player;
	}

	public void update()
	{
		
	}
	
	public void render(Graphics2D g2d)
	{		
		for (int x = 0; x < multi.length; x++) {
			for (int y = 0; y < multi[x].length; y++) {
				
				int pixelX = xOffset + ((padding+cellSize)*x);
				int pixelY = yOffset + ((padding+cellSize)*y);
				
				multi[x][y].render(g2d, pixelX, pixelY, cellSize);
			}
		}
	}
	
}
