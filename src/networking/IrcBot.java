package networking;

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
			
		}
		
		// If we're talking with the matched user...
		if (Main.ircConnection.matchedUser != null && Main.ircConnection.matchedUser.equals(sender)) {
			
			// If they send us their correct char button, store it
			if (message.toUpperCase().startsWith("CORRECT_CHAR_BUTTON:")) {
				
				String[] messageParts = message.split(":");
				
				char partnerCorrectChar = messageParts[1].charAt(0);
				
				System.out.println("Partner's correct button: "+partnerCorrectChar);
				
				Main.ircConnection.partnerCorrectChar = partnerCorrectChar;
			}
			else if (message.equalsIgnoreCase("UNMATCH")) {
				
				Main.ircConnection.matchedUser = null;
			}
		}
		
		
	}

}
