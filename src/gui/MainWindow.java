package gui;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements Runnable
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
	
	}

	public void run()
	{
		this.setVisible(true);
		
	}
	
}