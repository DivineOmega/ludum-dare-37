package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements KeyListener, Runnable
{
	public DrawPane drawPane;

	public MainWindow()  
	{
		drawPane = new DrawPane();
		add(drawPane);
		
		setResizable(false);
		setSize(800, 600);
		setTitle("One Room (Ludum Dare 37)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e)
	{
	    
	}

	public void keyReleased(KeyEvent arg0) 
	{
		
	}

	public void keyTyped(KeyEvent arg0) 
	{
		
	}

	public void run()
	{
		this.setVisible(true);
		
	}
	
}