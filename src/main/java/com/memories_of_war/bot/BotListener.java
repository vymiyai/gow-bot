package com.memories_of_war.bot;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.hooks.EventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BotListener implements EventListener {

    public static final String FOLLOW_ME = "Follow me.";

    @Value("${discord.botName}")
    private String botName;

    private JDA jda;
    private Guild server;
    private Role vvRole;
    private TextChannel botRoom;
    private TextChannel worldChat;
    private long leaderId;
    private long ownId;

    private BotState state = new ReadyState();

    @Override
    public void onEvent(Event event) {
        state.handle(this, event);
    }

    @Scheduled(fixedRate = 1000)
    public void onScheduledJob() {
        state.handleTimed(this);
    }

    public String getBotName() {
        return botName;
    }

    public JDA getJda() {
        return jda;
    }

    public Guild getServer() {
        return server;
    }

    public Role getVvRole() {
        return vvRole;
    }

    public TextChannel getBotRoom() {
        return botRoom;
    }

    public void setJda(JDA jda) {
        this.jda = jda;
    }

    public void setServer(Guild server) {
        this.server = server;
    }

    public void setVvRole(Role vvRole) {
        this.vvRole = vvRole;
    }

    public void setBotRoom(TextChannel botRoom) {
        this.botRoom = botRoom;
    }

    public void setState(BotState state) {
        this.state = state;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public long getOwnId() {
        return ownId;
    }

    public void setOwnId(long ownId) {
        this.ownId = ownId;
    }

    public TextChannel getWorldChat() {
        return worldChat;
    }

    public void setWorldChat(TextChannel worldChat) {
        this.worldChat = worldChat;
    }
}
