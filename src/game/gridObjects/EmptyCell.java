package game.gridObjects;

import game.GridObject;

import java.awt.Color;
import java.awt.Graphics2D;

public class EmptyCell implements GridObject {
		
	public void update()
	{
		
	}
	
	public void render(Graphics2D g2d, int pixelX, int pixelY, int cellSize)
	{
		g2d.setColor(Color.white);
		g2d.fillRect(pixelX, pixelY, cellSize, cellSize);
	}

}
