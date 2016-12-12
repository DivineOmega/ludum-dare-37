package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Grid;
import game.Player;
import gui.MainWindow;

public class GameLoop {
	
	private MainWindow mainWindow;
	
	private long timer = 0;
	private long tickInterval = 10;
	
	private long renderTimer = 0;
	private int framesPerSecond = 60;
	
	private Grid grid = new Grid();
	private Player player = new Player();
	
	private ArrayList<Integer> keysPressed = new ArrayList<Integer>();

	public GameLoop(MainWindow mainWindow) {
		
		this.mainWindow = mainWindow;
		
		setupKeyHandler();
		init();
		
	}
	
	private void setupKeyHandler()
	{
		this.mainWindow.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int index = keysPressed.indexOf(e.getKeyCode());
				if (index >= 0 && index <= keysPressed.size()-1) {
					keysPressed.remove(index);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (!keysPressed.contains(e.getKeyCode())) {
					keysPressed.add(e.getKeyCode());
				}
			}
		});
	}
	
	public void init()
	{
		// Init grid
		grid.init();
				
		// Set up buttons
		grid.setupButtons();
		
		// Reposition player
		player.reposition(grid);
	}

	public void start()
	{
		long lastTime = System.currentTimeMillis();
		long currentTime;
		long elapsedTime;

		
		while(true)
		{
			currentTime = System.currentTimeMillis();
			elapsedTime = currentTime - lastTime;
			
			handleInput();
			update(elapsedTime);
			render(elapsedTime);
			
			lastTime = currentTime;
		}
	}
	
	private void handleInput()
	{		
		player.handleInput(keysPressed);
	}
	
	private void update(long elapsedTime)
	{
		timer += elapsedTime;
		
		if (timer<tickInterval)
		{
			return;
		}
		
		timer = 0;
		
		if (Main.computerWindow.isVisible()) {
			keysPressed.clear();
			return;
		}
		
		// Update grid
		grid.update();
		
		// Update player
		player.update(grid);
		
	}
	
	private void render(long elapsedTime)
	{
		renderTimer += elapsedTime;
		
		// Only render a fixed number of frames per second
		if (renderTimer <= 1000 / framesPerSecond )
		{
			return;
		}
		
		renderTimer = 0;
		
		// Create render image
		BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		
		// Set background colour
		g2d.setColor(Color.darkGray);
		g2d.fillRect(0, 0, 800, 800);
		
		if (Main.computerWindow.isVisible()) {
			
			g2d.setColor(Color.white);
			g2d.drawString("You must close the computer chat window to continue.", 240, 280);
			
		} else {
		
			// Draw grid
			grid.render(g2d);
			
			// Draw player
			player.render(g2d, grid.cellSize);
			
			// Draw scores
			g2d.setColor(Color.white);
			g2d.drawString("Score: "+Main.score, 575, 50);
			g2d.drawString("High score: "+Main.highScore, 575, 75);
			
			// Draw instructions
			g2d.drawString("Instructions:", 575, 180);
			g2d.drawString("Only one of these buttons will", 575, 220);
			g2d.drawString("let you escape this room.", 575, 240);
			g2d.drawString("Use the computer to talk to", 575, 260);
			g2d.drawString("another player and help each", 575, 280);
			g2d.drawString("other escape.", 575, 300);
			g2d.drawString("Escaping a room will give you", 575, 340);
			g2d.drawString("1 point, but pressing the wrong", 575, 360);
			g2d.drawString("button will reset your score", 575, 380);
			g2d.drawString("to zero. Be careful!", 575, 400);
			
			g2d.drawString("Press SPACE to interact.", 575, 440);
			g2d.drawString("Press ARROW KEYS to move.", 575, 460);
		}
				
		// Repaint with new render image
		mainWindow.drawPane.image = image;
		mainWindow.repaint();

	}
	
}
