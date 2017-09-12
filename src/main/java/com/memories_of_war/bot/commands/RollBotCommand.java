package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Given a String in the format "xdy", rolls a y-sided die x times.
 *
 * @author Darkagma
 */
@Component
public class RollBotCommand implements IBotCommand {

    public final Integer X_LOWER_BOUND_INCLUSIVE = 1;
    public final Integer X_UPPER_BOUND_INCLUSIVE = 10;
    public final Integer Y_LOWER_BOUND_INCLUSIVE = 1;
    public final Integer Y_UPPER_BOUND_INCLUSIVE = 9999;

    private String pattern = "(\\d+)[dD](\\d+)";

    public String getErrorMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("The command must be in the form \"!roll xdy\", where ");
        sb.append(this.X_LOWER_BOUND_INCLUSIVE);
        sb.append(" <= x <= ");
        sb.append(this.X_UPPER_BOUND_INCLUSIVE);
        sb.append(" and ");
        sb.append(this.Y_LOWER_BOUND_INCLUSIVE);
        sb.append(" <= y <= ");
        sb.append(this.Y_UPPER_BOUND_INCLUSIVE);
        sb.append(" (e.g. \"!roll 1d6\").");

        return sb.toString();

    }

    @Override
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {

        String response;

        try {
            // retrieve values of x and y.
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(tokenizedMessage[1]);
            m.matches();

            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));

            // throw error if values are not in the expected bounds.
            if (x < this.X_LOWER_BOUND_INCLUSIVE || x > this.X_UPPER_BOUND_INCLUSIVE)
                throw new Exception("x out of bounds!");

            if (y < this.Y_LOWER_BOUND_INCLUSIVE || y > this.Y_UPPER_BOUND_INCLUSIVE)
                throw new Exception("y out of bounds!");

            // generate x random numbers from 1 to y.
            Random random = new Random();

            String[] numbers = new String[x];
            for (int i = 0; i < numbers.length; i++) {
                Integer number = random.nextInt(y) + 1;
                numbers[i] = number.toString();
            }

            // return result as a list of numbers separated by a whitespace.
            response = String.join(" ", numbers);

        } catch (Exception e) {
            response = this.getErrorMessage();

        }

        // return the response without mentions if the event is null.
        if (event == null)
            event.getChannel().sendMessage(response);
        else
            event.getChannel().sendMessage(event.getAuthor().mention() + response);
    }

    @Override
    public String getCommandName() {
        return "!roll";
    }

    @Override
    public String getCommandDescription() {
        return "Type !roll xdy with x and y being positive integer numbers to roll x dices of y faces (e.g. !roll 1d6).";
    }
}
