package com.memories_of_war.bot.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.memories_of_war.bot.commands.RipBotCommand;

public class RipBotCommandTest {
	@Test
	public void testRip() {
		RipBotCommand rbc = new RipBotCommand();

		assertEquals("Serves you right! lol", rbc.execute(new String[] { "!rip" }, null));
	}
}
