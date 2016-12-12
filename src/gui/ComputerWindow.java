package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Main;
import net.miginfocom.swing.MigLayout;
import networking.IrcConnection;

public class ComputerWindow extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private JLabel label;
	private JScrollPane sp;

	/**
	 * Create the frame.
	 */
	public ComputerWindow() {
		
		setBounds(100, 100, 500, 500);
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Computer Chat - Chat Room One");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow][][]"));
		
		label = new JLabel("Status: ");
		contentPane.add(label, "cell 0 0");
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		sp = new JScrollPane(textArea);
		contentPane.add(sp, "cell 0 1,grow");
		
		textField = new JTextField();
		contentPane.add(textField, "flowx,cell 0 2,growx");
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		contentPane.add(btnSend, "cell 0 2");
		
		
		Action action = new AbstractAction()
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	if (textField.getText().trim().isEmpty()) {
					return;
				}
				
				char partnerCorrectChar = Main.ircConnection.getPartnerCorrectChar();
				
				if (textField.getText().toUpperCase().contains(Character.toString(partnerCorrectChar).toUpperCase())) {
					addToChat("You are not allowed to use '"+partnerCorrectChar+"' in your message.");
					return;
				}
				
				addToChat("You: "+textField.getText());
				Main.ircConnection.queueMessage("CHAT:"+textField.getText());
				textField.setText("");
		    }
		};

		btnSend.addActionListener(action);
		textField.addActionListener(action);
		
		JLabel lblFindNewPartner = new JLabel("<html>If your partner does not seem to be responding, <br>click the button below to try to find a new partner.");
		contentPane.add(lblFindNewPartner, "cell 0 3");
		
		JButton btnFindNewPartner = new JButton("Find new partner");
		contentPane.add(btnFindNewPartner, "cell 0 4");
		
		btnFindNewPartner.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setStatus("Finding new partner...");
				clearChat();
				Main.ircConnection.matchedUser = null;
				Main.ircConnection.sendMyCorrectChar();
			}
		});
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
