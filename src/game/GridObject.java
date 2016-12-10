package game;

import java.awt.Graphics2D;

public abstract interface GridObject {
		
	public void update();
	
	public void render(Graphics2D g2d, int pixelX, int pixelY, int cellSize);

}
