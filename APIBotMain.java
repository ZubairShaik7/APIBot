import java.io.IOException;

import org.jibble.pircbot.*;


public class APIBotMain {

	public static void main(String[] args) {
		APIBot bot = new APIBot();
		bot.setVerbose(true);
		try {
			bot.connect("irc.freenode.net");
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
		bot.joinChannel("#CS2336");

	}

}
