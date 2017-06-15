package com.memories_of_war.bot.commands;

import java.util.Random;

/**
 * Returns HEADS or TAILS.
 * @author vymiyai
 *
 */
public class FlipBotCommand implements IBotCommand {

	@Override
	public String execute(String[] tokenizedMessage) {
		
		// "Easter egg".
		if(tokenizedMessage[0].equals("!FLIP"))
			return "t(-.-)t";
		
		Random random = new Random();
		if (random.nextInt(2) == 0)
			return "HEADS";
		else
			return "TAILS";
	}

}
