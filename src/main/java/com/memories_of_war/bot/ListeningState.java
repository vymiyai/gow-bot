package com.memories_of_war.bot;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.LocalDateTime;

import static com.memories_of_war.bot.BotListener.FOLLOW_ME;

public class ListeningState implements BotState {

    private LocalDateTime creationDate;

    public ListeningState(BotListener bot) {
        creationDate = LocalDateTime.now();
        bot.getJda().getPresence().setPresence(Game.listening("users as Leader"), false);
        System.out.println("Listening as the leader.");
    }

    @Override
    public void handle(BotListener bot, Event event) {
        if (!(event instanceof MessageReceivedEvent)) {
            return;
        }

        MessageReceivedEvent mre = (MessageReceivedEvent) event;
        if(mre.getMessage().getContentRaw().equals(FOLLOW_ME)
                && mre.getAuthor().isBot()
                && mre.getAuthor().getIdLong() != bot.getOwnId()) {
            bot.setLeaderId(-1);
            bot.setState(new StartElectionState(bot));
            return;
        }

        if(mre.getMessage().getContentRaw().equals("Requesting election.")
                && mre.getAuthor().isBot()
                && mre.getAuthor().getIdLong() != bot.getOwnId()) {
            bot.setLeaderId(-1);
            bot.setState(new StartElectionState(bot));
        }
    }

    @Override
    public void handleTimed(BotListener bot) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(creationDate.plusSeconds(5L))) {
            bot.getBotRoom().sendMessage(FOLLOW_ME).queue();
            creationDate = now;
        }
    }
}
