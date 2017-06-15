package com.memories_of_war.bot.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.memories_of_war.bot.commands.RollBotCommand;

public class RollBotCommandTest {
	@Test
	public void testRoll() {
		RollBotCommand rbc = new RollBotCommand();

		StringBuilder sb = new StringBuilder();
		sb.append("The command must be in the form \"!roll xdy\", where ");
		sb.append(rbc.X_LOWER_BOUND_INCLUSIVE);
		sb.append(" <= x <= ");
		sb.append(rbc.X_UPPER_BOUND_INCLUSIVE);
		sb.append(" and ");
		sb.append(rbc.Y_LOWER_BOUND_INCLUSIVE);
		sb.append(" <= y <= ");
		sb.append(rbc.Y_UPPER_BOUND_INCLUSIVE);
		sb.append(".");

		String errorMessage = sb.toString();

		assertNotEquals(errorMessage, rbc.execute(new String[] { "!roll", "1d6" }));
		assertNotEquals(errorMessage, rbc.execute(new String[] { "!roll", "10d9999" }));

		assertEquals(errorMessage, rbc.execute(new String[] { "!roll", "0d6" }));
		assertEquals(errorMessage, rbc.execute(new String[] { "!roll", "5d10000" }));

		assertEquals(errorMessage, rbc.execute(new String[] { "!roll", "asdfasdfsd" }));
	}

}
