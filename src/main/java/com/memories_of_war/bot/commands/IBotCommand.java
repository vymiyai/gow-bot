package com.memories_of_war.bot.commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * 
 * @author Darkagma Interface for simple bot commands.
 */
public interface IBotCommand {

	public void execute(String[] tokenizedMessage, MessageReceivedEvent event);

	public String getCommandName();

	public String getCommandDescription();
}
