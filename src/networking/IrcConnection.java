package networking;

import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class IrcConnection extends Thread {
	
	private String ircServer = "178.62.37.117";
	private String ircChannel = "#LD37";
	
	private ArrayList<String> messagesToSend = new ArrayList<String>();

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
		
			if (!ircBot.isConnected()) {
				ircBot.connect(ircServer);
				ircBot.joinChannel(ircChannel);
			}
			
			for (String messageToSend : messagesToSend) {
				ircBot.sendMessage(ircChannel, messageToSend);
			}
			
			Thread.sleep(1000);
			
		}
	}

	public void run() {
		setup();
	}

}
