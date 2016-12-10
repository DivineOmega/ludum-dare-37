package game.gridObjects;

import game.GridObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class CharButton implements GridObject {
	
	public char buttonChar;
	public boolean hoveredOver = false;
	public boolean correct = false;
	
	public CharButton()
	{
		Random random = new Random();
		
		buttonChar = (char) (48 + random.nextInt(74));
	}
		
	public void update()
	{
		hoveredOver = false;
	}
	
	public void render(Graphics2D g2d, int pixelX, int pixelY, int cellSize)
	{
		if (hoveredOver) {
			g2d.setColor(Color.orange);
		} else {
			g2d.setColor(Color.red);
		}
		g2d.fillRect(pixelX, pixelY, cellSize, cellSize);
		
		if (hoveredOver) {
			g2d.setColor(Color.black);
		} else {
			g2d.setColor(Color.white);
		}
		g2d.drawString(Character.toString(buttonChar), (int) (pixelX+(cellSize*0.4)), (int) (pixelY+(cellSize*0.6)));
	}
	
}
