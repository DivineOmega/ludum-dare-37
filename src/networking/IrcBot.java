package networking;

import java.math.BigInteger;
import java.util.Random;

import org.jibble.pircbot.*;

public class IrcBot extends PircBot {
	
	public IrcBot() {
		
		Random random = new Random();
		
		String name = "LD37_" + (new BigInteger(130, random)).toString(32).substring(0, 10);
		
        this.setName(name);
    }
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		
		
		
	}

}
