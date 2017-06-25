package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class RenameBotCommand extends JoinBotCommand implements IBotCommand {

	@Override
	@Transactional
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandName() {
		return "!rename";
	}

	@Override
	public String getCommandDescription() {
		return "Type !rename your_new_name to change your application user name.";
	}

}
