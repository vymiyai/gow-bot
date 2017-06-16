package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

@Component
public class RipBotCommand implements IBotCommand {

	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {

		String mention = "";
		if (event != null) {
			for (IUser user : event.getMessage().getMentions())
				mention += user.mention() + " ";
		}

		return mention + "Serves you right! lol";
	}
	
	
	@Override
	public String getCommandName() {
		return "!rip";
	}

}
