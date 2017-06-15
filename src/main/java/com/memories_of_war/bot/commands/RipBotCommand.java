package com.memories_of_war.bot.commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

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

}
