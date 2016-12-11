package networking;

import gui.MainWindow;

import java.math.BigInteger;
import java.util.Random;

import main.Main;

import org.jibble.pircbot.*;

public class IrcBot extends PircBot {
	
	public IrcBot() {
		
		Random random = new Random();
		
		String name = "LD37_" + (new BigInteger(130, random)).toString(32).substring(0, 10);
		
        setName(name);
        
        setMessageDelay(10);
    }
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		
		
		
	}
	
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		
		// If a bot is asking if we're already matched...
		if (message.equalsIgnoreCase("MATCHED?")) {
			
			// If we're not matched, tell them and match with them
			if (Main.ircConnection.matchedUser == null) {
				
				sendMessage(sender, "NOT_MATCHED");
				Main.ircConnection.matchedUser = sender;
				
			}
			// If we are matched, just tell them 
			else {
				
				sendMessage(sender, "MATCHED");
			}
			
		}
		// If a bot is telling they're not matched...
		else if (message.equalsIgnoreCase("NOT_MATCHED")) {
			
			// If we're not matched, match with them
			if (Main.ircConnection.matchedUser == null) {
				Main.ircConnection.matchedUser = sender;
			}
			// If we are already matched to a user other than the sender, tell them to unmatch from us.
			else if (!Main.ircConnection.matchedUser.equals(sender)) {
				sendMessage(sender, "UNMATCH");
			}
			
		}
		
		// If we're talking with the matched user...
		if (Main.ircConnection.matchedUser != null && Main.ircConnection.matchedUser.equals(sender)) {
			
			// If they send us their correct char button, store it
			if (message.toUpperCase().startsWith("CORRECT_CHAR_BUTTON:")) {
				
				String[] messageParts = message.split(":");
				
				char partnerCorrectChar = messageParts[1].charAt(0);
				
				System.out.println("Partner's correct button: "+partnerCorrectChar);
				
				Main.ircConnection.setPartnerCorrectChar(partnerCorrectChar);
			}
			// If they send us a chat message. add it to the computer chat log
			else if (message.toUpperCase().startsWith("CHAT:")) {
				
				String[] messageParts = message.split(":");
				
				String chatMsg = messageParts[1];
				
				// Ignore chat messages that contain my character (these should not be sent) - helps prevent cheating!
				char myCorrectChar = Main.ircConnection.getMyCorrectChar();
				if (chatMsg.toUpperCase().contains(Character.toString(myCorrectChar).toUpperCase())) {
					return;
				}
				
				Main.computerWindow.addToChat(sender+": "+chatMsg);
			}
			// If they request we unmatch, do so.
			else if (message.equalsIgnoreCase("UNMATCH")) {
				
				Main.ircConnection.matchedUser = null;
			}
		}
		
		
	}

}
