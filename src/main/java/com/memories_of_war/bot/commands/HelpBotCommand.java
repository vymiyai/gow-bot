package com.memories_of_war.bot.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class HelpBotCommand implements IBotCommand {

	/*
	 * Type !join to register your Discord user into the database. Type !raffle
	 * to earn gems! (use with moderation) Type !res to check your available
	 * resources. Type !v2 @someone to increase the target's cooldown by 1 hour.
	 * Costs 20 gems to deploy. Type !char character_name to create a new
	 * character (max 1). Type !battalions to see all battalion-related
	 * arguments. Type !units to list available units. Type !mods to list
	 * available unit mods. Type !territories to list available territories.
	 * Type !stats to see the current V2 launch ranking.
	 */

	/**
	 * List of injected commands.
	 */
	private List<IBotCommand> commands;

	/**
	 * Autowired constructor.
	 * 
	 * @param commands
	 */
	@Autowired
	public HelpBotCommand(List<IBotCommand> commands) {
		this.commands = commands;
	}

	/**
	 * Dynamically lists all command descriptions.
	 */
	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		StringBuilder response = new StringBuilder();

		response.append("```");

		for (IBotCommand command : this.commands) {
			response.append(command.getCommandDescription());
			response.append("\n");
		}

		response.append("```");

		return response.toString();
	}

	@Override
	public String getCommandName() {
		return "!help";
	}

	@Override
	public String getCommandDescription() {
		return "Type !help to show this tooltip.";
	}

}
