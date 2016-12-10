package game;


import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	
	public int x = 0;
	public int y = 0;
	
	public void update()
	{
		
	}
	
	public void render(Graphics2D g2d, int cellSize)
	{
		g2d.setColor(Color.lightGray);
		g2d.fillRect(x, y, cellSize, cellSize);
	}
	
}
