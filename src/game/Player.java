package game;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import main.GameLoop;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import enums.Direction;
import game.gridObjects.CharButton;

public class Player {
	
	public int x = 0;
	public int y = 0;
	
	private int speed = 2;
		
	private Direction moveDir = null;
	private boolean actionButtonPressed = false;
		
	public void update(Grid grid)
	{
		if (moveDir!=null) {
			switch(moveDir) {
				case UP:
					y -= speed;
					break;
				
				case DOWN:
					y += speed;
					break;
					
				case LEFT:
					x -= speed;
					break;
					
				case RIGHT:
					x += speed;
					break;
					
				default:
					break;
					
			}
			moveDir = null;
		}
		
		int leftBound = grid.xOffset + grid.padding;
		int rightBound = grid.xOffset + ((grid.padding + grid.cellSize)*grid.width);
		int upBound = grid.yOffset + grid.padding;
		int downBound = grid.yOffset + ((grid.padding + grid.cellSize)*grid.height);
		
		while (x < leftBound) {
			x++;
		}
		
		while (x > rightBound - grid.cellSize) {
			x--;
		}
		
		while (y < upBound) {
			y++;
		}
		
		while (y > downBound - grid.cellSize) {
			y--;
		}
		
		int centreX = (int) (x + (grid.cellSize*0.5));
		int centreY = (int) (y + (grid.cellSize*0.5));
		
		GridObject closestGridObject = grid.getGridObjectAtPixelCoordinates(centreX, centreY);
				
		if (closestGridObject instanceof CharButton) {
			
			CharButton charButton = ((CharButton) closestGridObject);
			
			charButton.hoveredOver = true;
			
			if (actionButtonPressed) {
								
				if (charButton.correct) {
					System.out.println("Success!");
				} else {
					System.out.println("Fail!");
				}
				
				// Reset the game
				grid.reset();
				reposition(grid);
				grid.setupButtons();
			}
		}
	}
	
	public void render(Graphics2D g2d, int cellSize)
	{
		g2d.setColor(Color.lightGray);
		g2d.fillRect((int) (x + (cellSize*0.25)), (int) (y + (cellSize*0.25)), 
				(int) (cellSize*0.5), (int) (cellSize*0.5));
		
		g2d.setColor(Color.black);
		g2d.drawRect((int) (x + (cellSize*0.25)), (int) (y + (cellSize*0.25)), 
				(int) (cellSize*0.5), (int) (cellSize*0.5));
	}

	public void handleInput(ArrayList<Integer> keysPressed) {
				
		if (keysPressed.contains(KeyEvent.VK_UP)) {
			moveDir = Direction.UP;
		} else if (keysPressed.contains(KeyEvent.VK_DOWN)) {
			moveDir = Direction.DOWN;
		} else if (keysPressed.contains(KeyEvent.VK_LEFT)) {
			moveDir = Direction.LEFT;
		} else if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
			moveDir = Direction.RIGHT;
		}
		
		actionButtonPressed = keysPressed.contains(KeyEvent.VK_SPACE);
		
	}

	public void reposition(Grid grid) {
		Random random = new Random();
		x = grid.xOffset + ((grid.padding+grid.cellSize)*random.nextInt(grid.width));
		y = grid.yOffset + ((grid.padding+grid.cellSize)*random.nextInt(grid.height));
	}
	
}
