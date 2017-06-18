package com.memories_of_war.bot.commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * 
 * @author vymiyai Interface for simple bot commands.
 */
public interface IBotCommand {

	public String execute(String[] tokenizedMessage, MessageReceivedEvent event);

	public String getCommandName();
	
	public String getCommandDescription();
}
