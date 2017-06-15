package com.memories_of_war.bot.gow_bot;

import java.util.HashMap;

import com.memories_of_war.bot.commands.FlipBotCommand;
import com.memories_of_war.bot.commands.IBotCommand;
import com.memories_of_war.bot.commands.RipBotCommand;
import com.memories_of_war.bot.commands.RollBotCommand;
import com.memories_of_war.bot.commands.VVBotCommand;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

public class BasicCommandHandler {

	private HashMap<String, IBotCommand> basicCommands;

	public BasicCommandHandler() {
		// instantiate the basic commands.
		this.basicCommands = new HashMap<String, IBotCommand>();

		this.basicCommands.put("!roll", new RollBotCommand());
		this.basicCommands.put("!flip", new FlipBotCommand());
		this.basicCommands.put("!rip", new RipBotCommand());
		this.basicCommands.put("!vv", new VVBotCommand());
	}

	/**
	 * Splits a string by space character.
	 * 
	 * @param messageString
	 *            - the command as a String
	 * @return an array of String tokens.
	 */
	public String[] tokenize(String messageString) {

		return messageString.split(" ");
	}

	/**
	 * Helper function to make certain aspects of the bot easier to use.
	 * 
	 * @param channel
	 *            - target Discord IChannel where the response will be posted.
	 * @param message
	 *            - the String containing a response.
	 */
	private void sendMessage(IChannel channel, String message) {
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
		 * Kenjii vv rip - flip - roll help
		 */

		String messageString = event.getMessage().getContent();
		String[] tokenizedMessage = this.tokenize(messageString);
		String commandToken = tokenizedMessage[0].toLowerCase();

		if (this.basicCommands.containsKey(commandToken)) {
			IBotCommand command = this.basicCommands.get(commandToken);
			String response = command.execute(tokenizedMessage, event);
			this.sendMessage(event.getChannel(), response);
		}

		// do nothing if there is no command match.
	}

}
