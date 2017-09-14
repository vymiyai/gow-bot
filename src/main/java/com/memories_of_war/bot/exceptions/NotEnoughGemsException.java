package com.memories_of_war.bot.exceptions;

public class NotEnoughGemsException extends VVBotException {
    private static final long serialVersionUID = 6997702751873493193L;

    private Integer gems = 0;

    public NotEnoughGemsException(Integer gems) {
        super();
        this.gems = gems;
    }

    @Override
    public String getMessage() {
        return "You don't have " + this.gems + " :gem:! You fucking failure!";
    }
}
