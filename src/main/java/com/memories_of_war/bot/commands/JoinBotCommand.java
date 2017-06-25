package com.memories_of_war.bot.commands;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordResourcesRepository;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.UserAlreadyExistsException;
import com.memories_of_war.bot.exceptions.UsernameAlreadyExistsException;
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

	@Autowired
	protected DiscordResourcesRepository discordResourceRepository;

	@Autowired
	protected DiscordUserRepository discordUserRepository;

	protected Integer MAXIMUM_USERNAME_LENGTH = 32;

	protected String pattern = "^[a-zA-Z0-9_]+$";

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

	/**
	 * Checks if it there is are no DiscordUser associated with a discordId.
	 * @param discordId - the Discord UID.
	 * @return true or an exception otherwise.
	 * @throws UserAlreadyExistsException
	 */
	private boolean isFirstCharacter(Long discordId) throws UserAlreadyExistsException {
		List<DiscordUser> results = this.discordUserRepository.findByDiscordId(discordId);
		if (results.size() == 0)
			return true;
		else
			throw new UserAlreadyExistsException();
	}

	/**
	 * Checks if the user name is free to be used.
	 * @param username - the desired user name
	 * @return true or an exception otherwise.
	 * @throws UsernameAlreadyExistsException
	 */
	protected boolean isUsernameAvailable(String username) throws UsernameAlreadyExistsException {
		List<DiscordUser> results = this.discordUserRepository.findByDiscordUsername(username);
		if (results.size() == 0)
			return true;
		else
			throw new UsernameAlreadyExistsException();
	}

	@Override
	@Transactional
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		String mention = event.getAuthor().mention() + " ";
		Long discordId = event.getAuthor().getLongID();

		try {
			// check for exactly two arguments.
			if (tokenizedMessage.length != 2)
				throw new Exception("The !join command takes exactly one parameter (e.g. !join username).");

			String username = tokenizedMessage[1];

			this.isValidUsername(username);
			this.isFirstCharacter(discordId);
			this.isUsernameAvailable(username);

			// create the new user.
			DiscordUser newUser = new DiscordUser(discordId, username);

			// create the resources. Saving is not required because of DiscordUser's cascade.
			DiscordResources discordResources = new DiscordResources();
			newUser.setDiscordResources(discordResources);
			
			// should work because of cascade.
			this.discordUserRepository.save(newUser);

			return mention + "User " + username + " created.";
		} catch (Exception e) {
			return mention + e.getMessage();
		}
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
