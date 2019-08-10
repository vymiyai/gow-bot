package com.memories_of_war.bot;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.LocalDateTime;

import static com.memories_of_war.bot.BotListener.FOLLOW_ME;

public class WaitLeaderState implements BotState {

    private LocalDateTime creationTimestamp;

    public WaitLeaderState(BotListener bot) {
        creationTimestamp = LocalDateTime.now();
        bot.getJda().getPresence().setPresence(Game.listening("Leader reply"), false);
        System.out.println("Awaiting Leader reply.");
    }

    @Override
    public void handle(BotListener bot, Event event) {
        if (!(event instanceof MessageReceivedEvent)) {
            return;
        }

        MessageReceivedEvent mre = (MessageReceivedEvent) event;
        if (!mre.getTextChannel().equals(bot.getBotRoom())) {
            return;
        }

        if (mre.getAuthor().getIdLong() != bot.getOwnId()
                &&mre.getAuthor().isBot()
                && mre.getMessage().getContentRaw().equals(FOLLOW_ME)) {
            bot.setLeaderId(mre.getAuthor().getIdLong());
            bot.setState(new SleepState(bot));
        }
    }

    @Override
    public void handleTimed(BotListener bot) {
        if(LocalDateTime.now().isAfter(creationTimestamp.plusSeconds(10L))) {
            bot.getBotRoom().sendMessage("Requesting election.").queue();
            bot.setState(new StartElectionState(bot));
        }
    }

}
