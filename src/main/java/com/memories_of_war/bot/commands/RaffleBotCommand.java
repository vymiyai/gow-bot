package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class RaffleBotCommand implements IBotCommand {

	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
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