package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class GogogoCommand implements IBotCommand {

    @Override
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
        event.getChannel().sendMessage("https://i.imgur.com/YPhOCwj.png");
    }


    @Override
    public String getCommandName() {
        return "!gogogo";
    }

    @Override
    public String getCommandDescription() {
        return "Type !gogogo for *menacing aura*.";
    }

}
