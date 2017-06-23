package com.memories_of_war.bot.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.memories_of_war.bot.BasicCommandHandler;

public class BasicCommandHandlerTest {
	
	@Test
	public void testTokenize()
	{
		BasicCommandHandler bch = new BasicCommandHandler();
		
		assertEquals(1, bch.tokenize("").length);
		assertEquals(2, bch.tokenize("!vv bitch").length);
		assertEquals(1, bch.tokenize("!resources").length);
	}
}
