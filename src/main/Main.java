package main;

import javax.swing.SwingUtilities;

import gui.MainWindow;
import networking.IrcConnection;

public class Main {
	
	public static IrcConnection ircConnection = new IrcConnection();
		
	public static void main(String[] args)
	{
		MainWindow mainWindow = new MainWindow();
		SwingUtilities.invokeLater(mainWindow);
		
		ircConnection.start();
		
		GameLoop gameLoop = new GameLoop(mainWindow);
		gameLoop.start();
		
	}
}
