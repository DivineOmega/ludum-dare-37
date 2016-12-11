package game.gridObjects;

import game.GridObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Computer implements GridObject {
	
	public boolean hoveredOver = false;
	
	public Computer()
	{
		
	}
		
	public void update()
	{
		hoveredOver = false;
	}
	
	public void render(Graphics2D g2d, int pixelX, int pixelY, int cellSize)
	{
		if (hoveredOver) {
			g2d.setColor(Color.cyan);
		} else {
			g2d.setColor(Color.blue);
		}
		g2d.fillRect(pixelX, pixelY, cellSize, cellSize);
		
		if (hoveredOver) {
			g2d.setColor(Color.black);
		} else {
			g2d.setColor(Color.white);
		}
		g2d.drawString("Comp-", (int) (pixelX+(cellSize*0.01)), (int) (pixelY+(cellSize*0.5)));
		g2d.drawString("uter", (int) (pixelX+(cellSize*0.01)), (int) (pixelY+(cellSize*0.8)));
		
	}
	
}
