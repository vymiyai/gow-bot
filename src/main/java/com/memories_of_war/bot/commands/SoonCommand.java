package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.Image;

import java.util.Random;

@Component
public class SoonCommand implements IBotCommand {

    @Override
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
        event.getChannel().sendMessage("https://i.imgur.com/UgP7Q83.png");
    }


    @Override
    public String getCommandName() {
        return "!soon";
    }

    @Override
    public String getCommandDescription() {
        return "Type !soon for COMING SOON™.";
    }

}
