package com.memories_of_war.bot.exceptions;

public class WrongNumberOfArgumentsException extends VVBotException {

    private String errorMessage;

    public WrongNumberOfArgumentsException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
