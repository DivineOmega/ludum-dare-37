package game.gridObjects;

import game.GridObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class CharButton implements GridObject {
	
	public char buttonChar;
	public boolean hoveredOver = false;
	public boolean correct = false;
	
	public CharButton(ArrayList<CharButton> existingCharButtons)
	{
		Random random = new Random();
		
		boolean charInUse = false;		
		
		do {
			
			// Pick a random char
			buttonChar = (char) (48 + random.nextInt(74));
			
			// Assume char is not in use
			charInUse = false;
			
			// Check if it actually is in use already by an existing button
			for (CharButton existingCharButton : existingCharButtons) {
				if (existingCharButton.buttonChar==buttonChar) {
					charInUse = true;
				}
			}
			
			// Repeat if this char is in use
		} while (charInUse);
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
