package com.memories_of_war.bot.exceptions;

public class NotEnoughGemsException extends Exception {
	private static final long serialVersionUID = 6997702751873493193L;
	private Integer gems;
	
	public NotEnoughGemsException(Integer gems) {
		super();
		this.gems = gems;
	}
	
	@Override
	public String getMessage() {
		return "You don't have " + this.gems + " :gem:! You fucking failure!";
	}
}
