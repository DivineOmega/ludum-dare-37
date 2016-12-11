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
		setTitle("Chat Room One");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
			
	}

	public void run()
	{
		this.setVisible(true);
		
	}
	
	
}