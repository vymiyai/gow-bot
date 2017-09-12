package com.memories_of_war.bot.exceptions;

import com.memories_of_war.bot.commands.V2BotCommand;

public class NotEnoughGemsException extends VVBotException {
	private static final long serialVersionUID = 6997702751873493193L;

	@Override
	public String getMessage() {
		return "You don't have " + V2BotCommand.V2_ROCKET_GEM_COST + " :gem:! You fucking failure!";
	}
}
