package com.memories_of_war.bot.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.memories_of_war.bot.CommandHandler;

public class CommandHandlerTest {
	
	@Test
	public void testTokenize()
	{
		CommandHandler bch = new CommandHandler();
		
		assertEquals(1, bch.tokenize("").length);
		assertEquals(2, bch.tokenize("!vv bitch").length);
		assertEquals(1, bch.tokenize("!resources").length);
	}
}
