package com.memories_of_war.bot.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class StatsBotCommand implements IBotCommand {

	@Autowired
	private DiscordUserRepository dur;

	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {

		String mention = event.getAuthor().mention() + " ";

		Long discordId = event.getAuthor().getLongID();
		List<DiscordUser> users = dur.findByDiscordId(discordId);

		try {
			if (users.isEmpty())
				throw new UserDoesNotExistException();

			// since discordId has a uniqueness guarantee from the schema, there
			// should be only 1 or 0 results.
			DiscordUser user = users.get(0);
			DiscordResources resources = user.getDiscordResources();

			StringBuilder sb = new StringBuilder();
			sb.append(mention);
			sb.append("\n\n");
			sb.append(user.getDiscordUsername());
			sb.append("'s statistics:\n\n");
			sb.append(":gem: Gems: ").append(resources.getGems()).append("/1000 | ");
			sb.append("Gems spent: ").append(resources.getSpentGems()).append("\n");
			sb.append(":moneybag: Gold: ").append(resources.getGold()).append("/10000 | ");
			sb.append("Gold  spent: ").append(resources.getSpentGold()).append("\n\n");
			sb.append("V2 rockets launched: ").append(resources.getV2Launched());

			return sb.toString();
		} catch (Exception e) {
			// return error message if user has no registered characters.
			return mention + e.getMessage();
		}
	}

	@Override
	public String getCommandName() {
		return "!stats";
	}

	@Override
	public String getCommandDescription() {
		return "Type !stats to check your statistics.";
	}

}
