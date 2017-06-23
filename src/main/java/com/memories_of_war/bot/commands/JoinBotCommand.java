package com.memories_of_war.bot.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.memories_of_war.bot.exceptions.UsernameTooLongException;
import com.memories_of_war.bot.exceptions.UsernameWithInvalidCharactersException;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Creates a new DiscordUser in the database if the following applies: 1 - user
 * has no registered character yet. 2 - user name is valid. 3 - user name
 * doesn't exist yet.
 * 
 * @author vymiyai
 *
 */
@Component
public class JoinBotCommand implements IBotCommand {

	protected Integer MAXIMUM_USERNAME_LENGTH = 32;

	protected String pattern = "^[a-zA-Z0-9]+$";

	/**
	 * Checks if the provided user name is valid - alphanumeric and underscores
	 * and at most 32 characters length.
	 * 
	 * @param username
	 * @return whether the user name is valid or not.
	 * @throws UsernameTooLongException
	 * @throws UsernameWithInvalidCharactersException
	 */
	protected boolean isValidUsername(String username)
			throws UsernameTooLongException, UsernameWithInvalidCharactersException {
		// test length.
		if (username.length() > this.MAXIMUM_USERNAME_LENGTH)
			throw new UsernameTooLongException();

		// test for forbidden characters.
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(username);
		if (!m.matches())
			throw new UsernameWithInvalidCharactersException();

		return true;
	}

	protected boolean isFirstCharacter(Long discordId) {
		return false;
	}

	protected boolean isUsernameAvailable(String username) {
		return false;
	}

	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		// check for exactly two arguments.
		if (tokenizedMessage.length != 2)
			return "The !join command takes exactly one parameter (e.g. !join username).";

		String username = tokenizedMessage[1];

		try {
			boolean isValid = this.isValidUsername(username);
			if (isValid) {
				// create the new user.
			}

		} catch (UsernameTooLongException e) {
			return e.getMessage();
		} catch (UsernameWithInvalidCharactersException e) {
			return e.getMessage();
		}

		return "User " + username + " created.";
	}

	@Override
	public String getCommandName() {
		return "!join";
	}

	@Override
	public String getCommandDescription() {
		return "Type !join <your_name_here> to register your Discord user into the database.";
	}

}
