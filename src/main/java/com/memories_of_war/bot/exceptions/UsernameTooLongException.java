package com.memories_of_war.bot.exceptions;

public class UsernameTooLongException extends Exception {
	private static final long serialVersionUID = -137827557684563072L;

	@Override
	public String getMessage() {
		return "Error: Character name length exceeds 32 characters.";
	}
}
