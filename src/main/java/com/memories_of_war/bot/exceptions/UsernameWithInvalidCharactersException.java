package com.memories_of_war.bot.exceptions;

public class UsernameWithInvalidCharactersException extends VVBotException {
	private static final long serialVersionUID = -1905984250063687540L;

	@Override
	public String getMessage() {
		return "Error: Character name must contain only alphanumeric symbols and underscores (\"_\").";
	}
}
