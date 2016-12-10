package main;

import javax.swing.SwingUtilities;

import gui.MainWindow;

public class Main {
		
	public static void main(String[] args)
	{
		MainWindow mainWindow = new MainWindow();
		SwingUtilities.invokeLater(mainWindow);
		
		GameLoop gameLoop = new GameLoop(mainWindow);
		gameLoop.start();
	}
}
