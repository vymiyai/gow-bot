package com.memories_of_war.bot.exceptions;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 8212613998239721655L;

	@Override
	public String getMessage() {
		return "Error: Discord user already exists.";
	}
}
