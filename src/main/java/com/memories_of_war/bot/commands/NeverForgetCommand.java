package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class NeverForgetCommand implements IBotCommand {

    @Override
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
        event.getChannel().sendMessage("https://www.youtube.com/watch?v=l5lmykGQaVw");
    }


    @Override
    public String getCommandName() {
        return "!neverforget";
    }

    @Override
    public String getCommandDescription() {
        return "Type !neverforget to never forget what we fight for.";
    }

}
