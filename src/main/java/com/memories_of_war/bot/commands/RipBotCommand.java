package com.memories_of_war.bot.commands;

import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

@Component
public class RipBotCommand implements IBotCommand {

    private final Long GOW_BOT_ID = 318068287692865536L;

    @Override
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {

        String mention = "";
        if (event != null) {
            for (IUser user : event.getMessage().getMentions())
                if (this.GOW_BOT_ID.equals(user.getLongID())) {
                    event.getChannel().sendMessage("Nobody tells me to !rip myself!");
                    return;
                } else
                    mention += user.mention() + " ";
        }

        event.getChannel().sendMessage(mention + "Serves you right! lol");
    }


    @Override
    public String getCommandName() {
        return "!rip";
    }

    @Override
    public String getCommandDescription() {
        return "Type !rip @someone for additional VietnamVetness.";
    }

}
