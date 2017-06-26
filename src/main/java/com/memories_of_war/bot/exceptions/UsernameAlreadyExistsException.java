package com.memories_of_war.bot.exceptions;

public class UsernameAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -7690295191279810274L;

	@Override
	public String getMessage() {
		return "Error: The requested character name is already in use.";
	}
}
