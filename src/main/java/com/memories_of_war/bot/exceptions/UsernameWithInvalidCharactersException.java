package com.memories_of_war.bot.exceptions;

public class UsernameWithInvalidCharactersException extends Exception {
	private static final long serialVersionUID = -1905984250063687540L;

	@Override
	public String getMessage() {
		return "Error: User name must contain only alphanummeric symbols and underscores(\"_\").";
	}
}
