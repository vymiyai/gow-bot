package com.memories_of_war.bot;

import net.dv8tion.jda.core.events.Event;

public interface BotState {

    void handle(BotListener bot, Event event);
    void handleTimed(BotListener bot);
}
