package com.memories_of_war.bot.commands;

/**
 * 
 * @author vymiyai
 * Interface for simple bot commands.
 */
public interface IBotCommand{
	public String execute(String[] tokenizedMessage);
}
