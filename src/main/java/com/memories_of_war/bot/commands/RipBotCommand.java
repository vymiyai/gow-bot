package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

@Component
public class RipBotCommand implements IBotCommand {

	private final Long GOW_BOT_ID = 318068287692865536L;
	
	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {

		String mention = "";
		if (event != null) {
			for (IUser user : event.getMessage().getMentions())
				if(user.getLongID() == this.GOW_BOT_ID)
					return "Nobody tells me to !rip myself!";
				else 
					mention += user.mention() + " ";
		}

		return mention + "Serves you right! lol";
	}
	
	
	@Override
	public String getCommandName() {
		return "!rip";
	}
	
	@Override
	public String getCommandDescription() {
		return "Type !rip @someone for additional VietnamVetness.";
	}

}
