package com.memories_of_war.bot.commands;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class RenameBotCommand extends JoinBotCommand implements IBotCommand {

	@Override
	@Transactional
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		String mention = event.getAuthor().mention() + " ";
		Long discordId = event.getAuthor().getLongID();

		try {
			// check for exactly two arguments.
			if (tokenizedMessage.length != 2)
				throw new Exception("The !rename command takes exactly one parameter (e.g. !rename <character_name>).");

			String username = tokenizedMessage[1];

			this.isValidUsername(username);
			this.isUsernameAvailable(username);

			// retrieve existing DiscordUsers.
			List<DiscordUser> users = this.discordUserRepository.findByDiscordId(discordId);

			// discordIds are unique, so there are at most 1 DiscordUsers with
			// such id.
			if (users.isEmpty())
				throw new UserDoesNotExistException();

			// get the first and only entry.
			DiscordUser user = users.get(0);

			// get old username.
			String oldUsername = user.getDiscordUsername();

			// rename character.
			user.setDiscordUsername(username);

			return mention + "Character " + oldUsername + " successfully renamed to " + username + ".";
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return mention + e.getMessage();
		}
	}

	@Override
	public String getCommandName() {
		return "!rename";
	}

	@Override
	public String getCommandDescription() {
		return "Type !rename <character_name> to change your application user name.";
	}

}
