package com.memories_of_war.bot.commands;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Given a String in the format "xdy", rolls a y-sided die x times.
 * 
 * @author vymiyai
 *
 */
public class RollBotCommand implements IBotCommand {

	public final Integer X_LOWER_BOUND_INCLUSIVE = 1;
	public final Integer X_UPPER_BOUND_INCLUSIVE = 10;
	public final Integer Y_LOWER_BOUND_INCLUSIVE = 1;
	public final Integer Y_UPPER_BOUND_INCLUSIVE = 9999;

	private String pattern = "([1-9]\\d*)[dD](1-9]\\d*)";

	@Override
	public String execute(String[] tokenizedMessage) {
		try {
			// retrieve values of x and y.
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(tokenizedMessage[1]);

			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));

			// throw error if values are not in the expected bounds.
			if (x < this.X_LOWER_BOUND_INCLUSIVE && x > this.X_UPPER_BOUND_INCLUSIVE)
				throw new Exception();

			if (y < this.Y_LOWER_BOUND_INCLUSIVE && y > this.Y_UPPER_BOUND_INCLUSIVE)
				throw new Exception();

			// generate x random numbers from 1 to y.
			Random random = new Random();

			String[] numbers = new String[x];
			for (int i = 0; i < numbers.length; i++) {
				Integer number = random.nextInt(y) + 1;
				numbers[i] = number.toString();
			}

			// return result as a list of numbers separated by a whitespace.
			return String.join(" ", numbers);

		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			sb.append("The command must be in the form \"!roll xdy\", where ");
			sb.append(this.X_LOWER_BOUND_INCLUSIVE);
			sb.append(" <= x <= ");
			sb.append(this.X_UPPER_BOUND_INCLUSIVE);
			sb.append(" and ");
			sb.append(this.Y_LOWER_BOUND_INCLUSIVE);
			sb.append(" <= y <= ");
			sb.append(this.Y_UPPER_BOUND_INCLUSIVE);
			sb.append(".");

			return sb.toString();
		}
	}

}
