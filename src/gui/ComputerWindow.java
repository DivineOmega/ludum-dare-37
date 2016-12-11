package gui;

import java.awt.event.FocusAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Main;
import net.miginfocom.swing.MigLayout;

public class ComputerWindow extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private JLabel label;

	/**
	 * Create the frame.
	 */
	public ComputerWindow() {
		
		setBounds(100, 100, 500, 500);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Computer - One Room (Ludum Dare 37)");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow][][]"));
		
		label = new JLabel("Status: ");
		contentPane.add(label, "cell 0 0");
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		contentPane.add(textArea, "cell 0 1,grow");
		
		textField = new JTextField();
		contentPane.add(textField, "flowx,cell 0 2,growx");
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		contentPane.add(btnNewButton, "cell 0 2");

		
		
	}

	@Override
	public void run() {
		
	}
	
	public void showWindow()
	{
		setLocationRelativeTo(Main.mainWindow);
		setVisible(true);
	}
		
	public void setStatus(String status)
	{
		label.setText("<html>Status: "+status+"</html>");
	}
	
	public void addToChat(String newText)
	{
		textArea.setText(textArea.getText()+"\r\n"+newText);
	}
	
	public void clearChat()
	{
		textArea.setText("");
	}

}
