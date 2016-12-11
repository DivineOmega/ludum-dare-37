package networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import main.Main;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.User;

public class IrcConnection extends Thread {
	
	private String ircServer = "178.62.37.117";
	private String ircChannel = "#LD37";
	
	private ArrayList<String> messagesToSend = new ArrayList<String>();
	
	public String matchedUser = null;
	
	private char myCorrectChar = 0;
	private char partnerCorrectChar = 0;

	private void setup()
	{
		IrcBot ircBot = new IrcBot();
		
		ircBot.setVerbose(true);
		
		while(true) 
		{
		
			try {
				
				handler(ircBot);		
				
			} catch (NickAlreadyInUseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IrcException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
		
	}

	private void handler(IrcBot ircBot) throws NickAlreadyInUseException, IOException, IrcException, InterruptedException {
		
		while (true) {
		
			// Connect to server and join channel
			if (!ircBot.isConnected()) {
				Main.computerWindow.setStatus("Connecting to server...");
				Main.computerWindow.clearChat();
				ircBot.connect(ircServer);
				ircBot.joinChannel(ircChannel);
			}
			
			// Send match making requests
			if (matchedUser==null) {
				
				
				Main.computerWindow.setStatus("Finding partner...");
				Main.computerWindow.clearChat();
				
				// If we're matchmaking, it's safe to assume the partnerCorrectChar is outdated, so reset it
				partnerCorrectChar = 0;
				
				User[] users = ircBot.getUsers(ircChannel);
				Collections.shuffle(Arrays.asList(users));
				for (User user : users) {
					// Don't match make with yourself
					if (user.getNick().equals(ircBot.getNick())) {
						continue;
					}
					
					// Stop sending match making request if we're now matched
					if (matchedUser!=null) {
						break;
					}
					
					// Send match making request
					ircBot.sendMessage(user.getNick(), "MATCHED?");
					
					// Wait a while before sending the next match making request
					Thread.sleep(200);
				}
			}
			
			// If we're matched with a user
			if (matchedUser!=null) {
				
				String statusString = "Partnered with "+matchedUser+". ";
				
				if (partnerCorrectChar!=0) {
					statusString += "<br>";
					statusString += "<br>";
					statusString += "Your partner need to press the '"+partnerCorrectChar+"' button to escape.<br>";
					statusString += "Describe this button to your partner to help them escape, <br>";
					statusString += "but you can't use '"+partnerCorrectChar+"' in your messages. <br>";
					statusString += "<br>";
					statusString += "Also, ask your partner what you need to do to escape.";
				}
					
				Main.computerWindow.setStatus(statusString);
				
				ArrayList<String> sentMessages = new ArrayList<String>();
				
				// Send them any queued messages
				for (String messageToSend : messagesToSend) {
					ircBot.sendMessage(matchedUser, messageToSend);
					sentMessages.add(messageToSend);
				}
				
				// Remove messages that have been sent from the messages to send list
				for (String sentMessage : sentMessages) {
					messagesToSend.remove(sentMessage);
				}
				sentMessages.clear();
				
			}
			
			Thread.sleep(2000);
			
		}
	}

	public void queueMessage(String message)
	{
		messagesToSend.add(message);
	}
	
	public void unmatch(String message)
	{
		queueMessage("UNMATCH");
		matchedUser = null;
	}
	
	public void run() {
		setup();
	}

	public void setMyCorrectChar(char buttonChar) {
		myCorrectChar = buttonChar;
		queueMessage("CORRECT_CHAR_BUTTON:"+myCorrectChar);
		
	}
	
	public void setPartnerCorrectChar(char buttonChar) {
		partnerCorrectChar = buttonChar;
	}
	
	public char getMyCorrectChar()
	{
		return myCorrectChar;
	}
	
	public char getPartnerCorrectChar()
	{
		return partnerCorrectChar;
	}

}
