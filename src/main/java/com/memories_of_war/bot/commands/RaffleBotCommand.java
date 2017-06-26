package com.memories_of_war.bot.commands;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class RaffleBotCommand implements IBotCommand {

	private final Integer MAXIMUM_NUMBER_OF_GEMS = 1000;

	@Autowired
	private DiscordUserRepository dur;

	@Override
	@Transactional
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		String mention = event.getAuthor().mention() + " ";
		Long discordId = event.getAuthor().getLongID();

		try {
			DiscordUser user = dur.findByDiscordId(discordId);
			
			// throw error if user not found.
			if (user == null)
				throw new UserDoesNotExistException();

			DiscordResources resources = user.getDiscordResources();

			if (resources.getGems() == this.MAXIMUM_NUMBER_OF_GEMS)
				return mention + "maximum number of gems reached (" + this.MAXIMUM_NUMBER_OF_GEMS + " :gem:).";

			// calculate time difference.
			Long cooldownTimestamp = resources.getCooldown().getTime();
			Long now = new Date().getTime();
			Long diff = now - cooldownTimestamp;

			Long penalty;
			Long newCooldownTimestamp;
			String response;
			if (diff > 0) {
				// raffled after cooldown.

				// award gems.
				Random random = new Random();
				Integer gems = random.nextInt(10) + 1;

				if (resources.getGems() + gems > this.MAXIMUM_NUMBER_OF_GEMS)
					resources.setGems(this.MAXIMUM_NUMBER_OF_GEMS);
				else
					resources.setGems(resources.getGems() + gems);

				// calculate cooldown increment.
				penalty = 2L * 60 * 60 * 1000;
				newCooldownTimestamp = now + penalty;
				response = "found " + gems + " :gem:! You must wait for 2 hours before you try again.";
			} else {
				// raffled before cooldown. increment 1 hours.

				// calculate cooldown increment.
				penalty = 1L * 60 * 60 * 1000;
				newCooldownTimestamp = cooldownTimestamp + penalty;

				// calculate time for response.
				Long timeInterval = Math.abs(diff) + penalty;
				Integer seconds = (int) (timeInterval / 1000) % 60;
				Integer minutes = (int) ((timeInterval / (1000 * 60)) % 60);
				Integer hours = (int) ((timeInterval / (1000 * 60 * 60)) % 24);
				Integer days = (int) (timeInterval / (1000 * 60 * 60 * 24));

				response = "You didn't rest properly and strained your body. You must rest for one extra hour! You can try again in";

				if (days == 1)
					response += " 1 day";

				if (days > 1)
					response += " " + days + " days";

				if (hours == 1)
					response += " 1 hour";

				if (hours > 1)
					response += " " + hours + " hours";

				if (minutes == 1)
					response += " 1 minute";

				if (minutes > 1)
					response += " " + minutes + " minutes";

				if (seconds == 1)
					response += " 1 second";

				if (seconds > 1)
					response += " " + seconds + " seconds";

				response += ".";
			}

			resources.setCooldown(new Timestamp(newCooldownTimestamp));

			return mention + response;

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return mention + e.getMessage();
		}
	}

	@Override
	public String getCommandName() {
		return "!raffle";
	}

	@Override
	public String getCommandDescription() {
		return "Type !raffle to earn gems! (2 hours cooldown. Use with moderation)";
	}

}
