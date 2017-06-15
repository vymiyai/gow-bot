package com.memories_of_war.bot.gow_bot;

import java.util.HashMap;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

public class BasicCommandHandler {

	public String[] tokenize(String messageString) {
		
		return messageString.split( " " );
	}

	// Helper functions to make certain aspects of the bot easier to use.
	private void sendMessage(IChannel channel, String message) {

		// This might look weird but it'll be explained in another page.
		RequestBuffer.request(() -> {
			try {
				channel.sendMessage(message);
			} catch (DiscordException e) {
				System.err.println("Message could not be sent with error: ");
				e.printStackTrace();
			}
		});
	}

	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event) {
		/*
		 * Type !kenjii to summon Kenjii. Type !vv for awesomeness. Type
		 * !rip @someone for additional VietnamVetness. Type !flip to toss a
		 * coin. Type !roll xdy with x and y being positive integer numbers to
		 * roll x dices of y faces (e.g. !roll 1d6). Type !help to show this
		 * tooltip.
		 */

		// get command dictionary.
		// HashMap<String, >

		// match command with dictionary key
		// if exists, do stuff
		// else, do nothing.
		// for each command in command
		
		String messageString = event.getMessage().getContent();
		String[] tokenizedMessageString = this.tokenize(messageString);
/*
		if(tokenizedMessageString.length > 0){
		
			// send a message back.
		}
	*/	
		// not entirely sure whether an empty string can be sent 
	}

}
