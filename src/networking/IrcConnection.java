package networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.User;

public class IrcConnection extends Thread {
	
	private String ircServer = "178.62.37.117";
	private String ircChannel = "#LD37";
	
	private ArrayList<String> messagesToSend = new ArrayList<String>();
	
	public String matchedUser = null;
	
	char partnerCorrectChar;

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
				ircBot.connect(ircServer);
				ircBot.joinChannel(ircChannel);
			}
			
			// Send match making requests
			if (matchedUser==null) {
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
					
					ircBot.sendMessage(user.getNick(), "MATCHED?");
				}
			}
			
			// If we're matched with a user
			if (matchedUser!=null) {
				
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

}
