package com.memories_of_war.bot.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.memories_of_war.bot.commands.RollBotCommand;

public class RollBotCommandTest {
	@Test
	public void testRoll() {
		RollBotCommand rbc = new RollBotCommand();

		String errorMessage = rbc.getErrorMessage();

		assertNotEquals(errorMessage, rbc.execute(new String[] { "!roll", "1d6" }, null));
		assertNotEquals(errorMessage, rbc.execute(new String[] { "!roll", "10d9999" }, null));

		assertEquals(errorMessage, rbc.execute(new String[] { "!roll", "0d6" }, null));
		assertEquals(errorMessage, rbc.execute(new String[] { "!roll", "5d10000" }, null));

		assertEquals(errorMessage, rbc.execute(new String[] { "!roll", "asdfasdfsd" }, null));
	}

}
