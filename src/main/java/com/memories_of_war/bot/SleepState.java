package com.memories_of_war.bot;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.LocalDateTime;

public class SleepState implements BotState {

    private LocalDateTime creationTimestamp;

    public SleepState(BotListener bot) {
        creationTimestamp = LocalDateTime.now();
        bot.getJda().getPresence().setPresence(Game.watching("Leader"), true);
        System.out.println("Sleeping.");
    }

    @Override
    public void handle(BotListener bot, Event event) {
        if (!(event instanceof MessageReceivedEvent)) {
            return;
        }

        MessageReceivedEvent mre = (MessageReceivedEvent) event;
        if(mre.getMessage().getContentRaw().equals("Requesting election.")
                && mre.getAuthor().isBot()
                && mre.getAuthor().getIdLong() != bot.getOwnId()) {
            bot.setLeaderId(-1);
            bot.setState(new StartElectionState(bot));
            return;
        }

        if(mre.getAuthor().isBot() && mre.getAuthor().getIdLong() == bot.getLeaderId()) {
            creationTimestamp = LocalDateTime.now();
            return;
        }
    }

    @Override
    public void handleTimed(BotListener bot) {
        if (LocalDateTime.now().isAfter(creationTimestamp.plusSeconds(10L))) {
            bot.getBotRoom().sendMessage("Requesting election.").queue();
            bot.setLeaderId(-1);
            bot.setState(new StartElectionState(bot));
        }
    }

}
