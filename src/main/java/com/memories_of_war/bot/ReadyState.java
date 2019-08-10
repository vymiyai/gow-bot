package com.memories_of_war.bot;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;

import static com.memories_of_war.bot.Application.*;

public class ReadyState implements BotState {

    @Override
    public void handle(BotListener bot, Event event) {
        if (event instanceof ReadyEvent) {
            bot.setJda(event.getJDA());
            bot.setOwnId(bot.getJda().getSelfUser().getIdLong());
            bot.setServer(bot.getJda().getGuildById(SERVER_ID));
            bot.setBotRoom(bot.getServer().getTextChannelById(BOT_ROOM));
            bot.setVvRole(bot.getServer().getRoleById(VV_ROLE_ID));

            bot.getBotRoom().sendMessage("Requesting leader.").queue();
            System.out.println(bot.getBotName() + " is online.");

            bot.setState(new WaitLeaderState(bot));
        }
    }

    @Override
    public void handleTimed(BotListener bot) {
        // do nothing.
    }

}
