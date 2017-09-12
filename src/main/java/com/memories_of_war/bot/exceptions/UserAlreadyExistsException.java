package com.memories_of_war.bot.exceptions;

public class UserAlreadyExistsException extends VVBotException {

	private static final long serialVersionUID = 8212613998239721655L;

	@Override
	public String getMessage() {
		return "Error: There are no free character slots for a new character.";
	}
}
