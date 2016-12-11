package game;

import game.gridObjects.CharButton;
import game.gridObjects.Computer;
import game.gridObjects.EmptyCell;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import main.Main;
import networking.IrcConnection;

public class Grid {
	
	private GridObject[][] multi = null;
	
	private ArrayList<CharButton> charButtons = new ArrayList<CharButton>();
	
	public int width = 13;
	public int height = 13;
	
	public int cellSize = 40;
	public int padding = 1;
	
	public int xOffset = 30;
	public int yOffset = 20;
		
	public Grid()
	{
		init();
		
	}
	
	public void reset()
	{
		init();
	}
	
	public void init()
	{
		multi = new GridObject[width][height];
		
		for (int x = 0; x < multi.length; x++) {
			for (int y = 0; y < multi[x].length; y++) {
				multi[x][y] = new EmptyCell();
			}
		}
		
		int[] computerCoordinates = getEmptyCoordinates();
		
		int computerX = computerCoordinates[0];
		int computerY = computerCoordinates[1];
		
		multi[computerX][computerY] = new Computer();
	}
	
	public void update()
	{
		for (int x = 0; x < multi.length; x++) {
			for (int y = 0; y < multi[x].length; y++) {
				
				int pixelX = xOffset + ((padding+cellSize)*x);
				int pixelY = yOffset + ((padding+cellSize)*y);
				
				multi[x][y].update();
			}
		}
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
	
	private int[] getEmptyCoordinates()
	{
		Random random = new Random();
		
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		
		while(!(multi[x][y] instanceof EmptyCell)) {
			x = random.nextInt(width);
			y = random.nextInt(height);
		}
		
		int[] coordinates = {x, y};
		
		return coordinates;
		
	}

	public void placeCharButton(CharButton charButton) 
	{
		
		int[] coordinates = getEmptyCoordinates();
		
		int x = coordinates[0];
		int y = coordinates[1];
		
		multi[x][y] = charButton;
	}
	
	public GridObject getGridObjectAtPixelCoordinates(int targetX, int targetY) 
	{
		Point2D.Double target = new Point2D.Double(targetX, targetY);
		
		double lowestDistance = Double.MAX_VALUE;
		GridObject closestGridObject = null;
		
		for (int x = 0; x < multi.length; x++) {
			for (int y = 0; y < multi[x].length; y++) {
				
				int pixelX = xOffset + ((padding+cellSize)*x);
				int pixelY = yOffset + ((padding+cellSize)*y);
				
				int centreX = (int) (pixelX + (cellSize*0.5));
				int centreY = (int) (pixelY + (cellSize*0.5));
				
				double distance = target.distance(new Point2D.Double(centreX, centreY));
				
				if (distance<lowestDistance) {
					lowestDistance = distance;
					closestGridObject = multi[x][y];
				}
				
			}
		}
		
		return closestGridObject;
	}

	public void setupButtons() {
		charButtons.clear();
		for (int i = 0; i < 9; i++) {
			CharButton charButton = new CharButton(charButtons);
			charButtons.add(charButton);
			
			if (i==0) {
				charButton.correct = true;
				System.out.println("Correct button is: "+charButton.buttonChar);
			}
			
			placeCharButton(charButton);
		}
		
		Main.ircConnection.setMyCorrectChar(charButtons.get(0).buttonChar);
		
	}
	
	
}
