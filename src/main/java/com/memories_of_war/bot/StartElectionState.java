package com.memories_of_war.bot;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.memories_of_war.bot.BotListener.FOLLOW_ME;

public class StartElectionState implements BotState {

    private LocalDateTime creationTimestamp;
    private UUID uuid;
    private HashMap<UUID, Long> poll = new HashMap<>();

    public StartElectionState(BotListener bot) {
        creationTimestamp = LocalDateTime.now();
        bot.getJda().getPresence().setPresence(Game.playing("Leader election"), false);
        System.out.println("Requesting election.");
    }

    @Override
    public void handle(BotListener bot, Event event) {
        if(uuid == null) {
            uuid = UUID.randomUUID();
            poll.put(uuid, bot.getOwnId());
            bot.getBotRoom().sendMessage(uuid.toString()).queue();
            return;
        }

        if (!(event instanceof MessageReceivedEvent)) {
            return;
        }

        MessageReceivedEvent mre = (MessageReceivedEvent) event;
        if(!mre.getTextChannel().equals(bot.getBotRoom())) {
            return;
        }

        if (mre.getAuthor().getIdLong() != bot.getOwnId()
                && mre.getAuthor().isBot()
                && mre.getMessage().getContentRaw().equals(FOLLOW_ME)) {
            bot.setLeaderId(mre.getAuthor().getIdLong());
            bot.setState(new SleepState(bot));
            return;
        }

        if(mre.getAuthor().isBot() && mre.getAuthor().getIdLong() != bot.getOwnId()) {
            String contentRaw = mre.getMessage().getContentRaw();
            try {
                UUID parsedUuid = UUID.fromString(contentRaw);
                poll.put(parsedUuid, mre.getAuthor().getIdLong());
            } catch (IllegalArgumentException iae) {
                System.out.println("Received [" + contentRaw + "] is not a UUID.");
            }
        }
    }

    @Override
    public void handleTimed(BotListener bot) {
        if (LocalDateTime.now().isAfter(creationTimestamp.plusSeconds(10L))) {
            poll.forEach((botUuid, botId) -> System.out.println(botId + " -> " + botUuid.toString()));

            List<UUID> uuids = new ArrayList<>(poll.keySet());
            if(uuids.size() == 0) {
                bot.getBotRoom().sendMessage("Requesting election.").queue();
                bot.setLeaderId(-1);
                bot.setState(new StartElectionState(bot));
                return;
            }

            UUID firstUuid = uuids.get(0);
            long botId = poll.get(firstUuid);

            if(firstUuid.equals(uuid)) {
                bot.setLeaderId(botId);
                bot.getBotRoom().sendMessage(FOLLOW_ME).queue();
                bot.setState(new ListeningState(bot));
                return;
            }

            System.out.println("Acknowledging [" + botId + "] as leader.");
            bot.setLeaderId(poll.get(firstUuid));
            bot.setState(new SleepState(bot));
        }
    }
}
