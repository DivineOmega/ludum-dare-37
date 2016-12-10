package game;

import game.gridObjects.EmptyCell;

import java.awt.Graphics2D;

public class Grid {
	
	private GridObject[][] multi = null;
	
	public int width = 13;
	public int height = 13;
	
	public int cellSize = 40;
	public int padding = 1;
	
	public int xOffset = 30;
	public int yOffset = 20;
		
	public Grid()
	{
		multi = new GridObject[width][height];
		
		for (int x = 0; x < multi.length; x++) {
			for (int y = 0; y < multi[x].length; y++) {
				multi[x][y] = new EmptyCell();
			}
		}
		
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
