package com.memories_of_war.bot.commands;

import java.util.Random;

import org.springframework.stereotype.Component;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Returns HEADS or TAILS.
 * 
 * @author vymiyai
 *
 */
@Component
public class FlipBotCommand implements IBotCommand {

	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {

		String mention;
		if( event == null )
			mention = "";
		else
			mention = event.getAuthor().mention() + " ";
		
		// "Easter egg".
		if (tokenizedMessage[0].equals("!FLIP"))
			return "t(-.-)t";

		Random random = new Random();
		if (random.nextInt(2) == 0)
			return mention + "HEADS";
		else
			return mention + "TAILS";
	}
	
	
	@Override
	public String getCommandName() {
		return "!flip";
	}

	@Override
	public String getCommandDescription() {
		return "Type !flip to toss a coin. The result are HEADS or TAILS.";
	}
	
}
