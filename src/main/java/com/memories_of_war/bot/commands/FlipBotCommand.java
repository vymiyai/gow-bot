package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Random;

/**
 * Returns HEADS or TAILS (or flips the bird).
 *
 * @author Darkagma
 */
@Component
public class FlipBotCommand implements IBotCommand {

    private final String easterEggMessage = "t(-.-)t";

    @Override
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {

        String mention;
        if (event == null)
            mention = "";
        else
            mention = event.getAuthor().mention() + " ";

        // "Easter egg".
        if (tokenizedMessage[0].equals("!FLIP")){
            event.getChannel().sendMessage(this.easterEggMessage);
            return;
        }

        Random random = new Random();
        if (random.nextInt(2) == 0)
            event.getChannel().sendMessage("HEADS");
        else
            event.getChannel().sendMessage("TAILS");
    }


    @Override
    public String getCommandName() {
        return "!flip";
    }

    @Override
    public String getCommandDescription() {
        return "Type !flip to toss a coin. The result is either HEADS or TAILS.";
    }

}
