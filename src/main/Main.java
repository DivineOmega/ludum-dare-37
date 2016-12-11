package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.ComputerWindow;
import gui.MainWindow;
import networking.IrcConnection;

public class Main {
	
	public static IrcConnection ircConnection = new IrcConnection();
	
	public static MainWindow mainWindow = new MainWindow();
	public static ComputerWindow computerWindow = new ComputerWindow();
	
	public static int score = 0;
	public static int highScore = 0;
		
	public static void main(String[] args)
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(mainWindow);
		SwingUtilities.invokeLater(computerWindow);
				
		ircConnection.start();
		
		GameLoop gameLoop = new GameLoop(mainWindow);
		gameLoop.start();
		
	}
}
