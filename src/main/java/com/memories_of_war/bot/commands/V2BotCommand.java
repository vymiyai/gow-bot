package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class V2BotCommand implements IBotCommand {

	@Override
	@Transactional
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandName() {
		return "!v2";
	}

	@Override
	public String getCommandDescription() {
		return "Type !v2 @someone to increase the target's cooldown by 1 hour. Costs 20 gems to deploy.";
	}

}
