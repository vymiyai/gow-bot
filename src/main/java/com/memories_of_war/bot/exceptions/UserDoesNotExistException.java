package com.memories_of_war.bot.exceptions;

public class UserDoesNotExistException extends Exception {
	private static final long serialVersionUID = -1981028072536562667L;
	
	@Override
	public String getMessage() {
		return "Error: You have no registered characters. Use the !join character_name command first.";
	}
}
