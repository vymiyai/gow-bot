package com.memories_of_war.bot.commands;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.NotEnoughGemsException;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

@Component
public class V2BotCommand implements IBotCommand {

	private final Integer V2_ROCKET_GEM_COST = 20;

	@Autowired
	private DiscordUserRepository dur;

	private void updateUserResources(DiscordResources userResources) {
		// subtract gems from user.
		Integer gems = userResources.getGems();
		userResources.setGems(gems - this.V2_ROCKET_GEM_COST);

		// add gems to spent gems.
		Integer spentGems = userResources.getSpentGems();
		userResources.setSpentGems(spentGems + this.V2_ROCKET_GEM_COST);

		// increase V2 launched count.
		Integer v2Launched = userResources.getV2Launched();
		userResources.setV2Launched(v2Launched + 1);
	}

	private void updateTargetResources(DiscordResources targetResources) {
		// calculate time difference.
		Long cooldownTimestamp = targetResources.getCooldown().getTime();
		Long now = new Date().getTime();
		Long diff = now - cooldownTimestamp;

		// create new timestamp in miliseconds.
		Long penalty = 1L * 60 * 60 * 1000;
		Long newCooldownTimestamp;
		if (diff > 0)
			// add one hour to now timestamp.
			newCooldownTimestamp = now + penalty;
		else
			// add one hour to cooldownTimestamp.
			newCooldownTimestamp = cooldownTimestamp + penalty;

		targetResources.setCooldown(new Timestamp(newCooldownTimestamp));
	}

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

			DiscordResources userResources = user.getDiscordResources();

			// throw error if not enough gems for V2.
			if (userResources.getGems() < this.V2_ROCKET_GEM_COST)
				throw new NotEnoughGemsException(this.V2_ROCKET_GEM_COST);

			// get mentions in message. Throw error if none or more than one.
			List<IUser> mentions = event.getMessage().getMentions();
			if (mentions.isEmpty() || mentions.size() > 1)
				throw new Exception("Choose a single @target ffs.");

			// retrieve target user.
			IUser target = mentions.get(0);
			DiscordUser targetUser = dur.findByDiscordId(target.getLongID());

			// throw error if user doesn't exist in the database.
			if (targetUser == null)
				throw new Exception("Error: Target user " + target.getName() + " is not registered.");

			// update user's resources.
			this.updateUserResources(userResources);

			// update target's resources.
			DiscordResources targetResources = targetUser.getDiscordResources();
			this.updateTargetResources(targetResources);

			return mention + " launched a V2 rocket on " + target.mention()
					+ "! Target's !raffle cooldown increased by one hour.";

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return mention + e.getMessage();
		}
	}

	@Override
	public String getCommandName() {
		return "!v2";
	}

	@Override
	public String getCommandDescription() {
		return "Type !v2 @someone to increase the target's cooldown by 1 hour. Costs " + this.V2_ROCKET_GEM_COST
				+ " gems to deploy.";
	}

}
