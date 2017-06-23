package com.memories_of_war.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memories_of_war.bot.commands.IBotCommand;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

@Component
public class BasicCommandHandler {

	/**
	 * Dictionary that stores each command with its calling prefix.
	 */
	private HashMap<String, IBotCommand> basicCommands;

	/**
	 * Initializes the BasicCommandHandler with commands.
	 * 
	 * @param injectedBasicCommands
	 *            - injected list of IBotCommand instances.
	 */
	@Autowired
	public void setBasicCommands(List<IBotCommand> injectedBasicCommands) {
		// instantiate the basic commands.
		this.basicCommands = new HashMap<String, IBotCommand>();

		// compile hashmap from command list.
		for (IBotCommand command : injectedBasicCommands)
			this.basicCommands.put(command.getCommandName(), command);
	}

	/**
	 * @return a list of Strings containing the prefixes of each command.
	 */
	public List<String> getCommandPrefixes() {
		List<String> prefixes = new ArrayList<String>();
		prefixes.addAll(basicCommands.keySet());
		return prefixes;
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

	/**
	 * Method called when a message is received by the bot. The logic of each
	 * command is defined through a Command design pattern.
	 * 
	 * @param event
	 */
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event) {

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
