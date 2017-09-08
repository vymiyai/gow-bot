package com.memories_of_war.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memories_of_war.bot.commands.IBotCommand;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

@Component
public class CommandHandler {

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
	
	@EventSubscriber
	public void onUser(UserJoinEvent event)
	{
		// assign new user to the Select Faction role.
		IRole selectFaction = event.getGuild().getRolesByName("Select Faction").get(0);
		event.getUser().addRole(selectFaction);
		
		// assemble and return the welcome message.
		StringBuilder response = new StringBuilder();
		response.append(event.getUser().mention());
		response.append(" *and ofc they cater to the non existant newbies than the vets still playing everyday. sigh*\n\n");
		response.append("**Welcome to the community-managed March of War Discord server. I am VietnamVet-bot, in short, VV-bot.**\n\n");

		response.append("**Please state if you have played the game before, your main faction and how found this Discord server.**\n\n");
		
		response.append("```- Feel free to ask around for information about the original game's outcome, complain about the EA Spy or to get to know what the community has been doing to try to revive the game.\n\n");
		response.append("- Remember to mention to one of the moderators your main faction, so that you can gain access to the faction-specific chats. Users without a faction are regularly kicked from the server.\n\n");
		response.append("- For questions regarding the project Avant Guard or the organization Solace Workshop, please refer to the users with pink usernames or Nero. While some Solace Workshop members lurk this server regularly, the server and its moderators are not officially associated with the Avant Guard project nor Solace Workshop.\n\n");
		response.append("- For V²-bot specific commands, type !help for a list of available commands.\n\n");
		response.append("- If you know other players, be sure to tell them about us!```");

		this.sendMessage(event.getGuild().getGeneralChannel(), response.toString());
	}

}
