package com.memories_of_war.bot;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
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
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent mre = (MessageReceivedEvent) event;
            if (mre.getMessage().getContentRaw().equals(FOLLOW_ME)
                    && mre.getAuthor().isBot()
                    && mre.getAuthor().getIdLong() != bot.getOwnId()) {
                bot.setLeaderId(-1);
                bot.setState(new StartElectionState(bot));
                return;
            }

            if (mre.getMessage().getContentRaw().equals("Requesting election.")
                    && mre.getAuthor().isBot()
                    && mre.getAuthor().getIdLong() != bot.getOwnId()) {
                bot.setLeaderId(-1);
                bot.setState(new StartElectionState(bot));
                return;
            }

            if(mre.getMessage().getContentRaw().startsWith("!vv")) {
                mre.getTextChannel().sendMessage("Yeah, yeah, I'm alive ffs.").queue();
            }
        }

        if (event instanceof GuildMemberJoinEvent) {
            GuildMemberJoinEvent gmje = (GuildMemberJoinEvent) event;
            String greeting = gmje.getMember().getAsMention()
                    + " *and ofc they cater to the non existant newbies than the vets still playing everyday. sigh*\n\n"
                    + "Welcome to the community-managed March of War Discord server.\n\n"
                    + "**Please state your main faction and how found this Discord server. Failure to comply with this instruction will get you kicked from the server.**\n\n"
                    + "- Read the #rules. Stating ignorance of the rules as an excuse to justify non-compliant actions will not be tolerated.\n\n"
                    + "- Feel free to ask around for information about the original game's outcome, complain about the EA Spy or to get to know what the community has been doing to try to revive the game.\n\n"
                    + "- Remember to mention to one of the moderators your main faction, so that you can gain access to the faction-specific chats. Users without a faction are regularly kicked from the server.\n\n"
                    + "- For questions regarding the project Avant Guard or the organization Solace Workshop, please refer to #ask_solace_workshop. This server is not officially associated with the Avant Guard project nor with the entity Solace Workshop.\n\n"
                    + "- If you know other players, be sure to tell them about us!";

            bot.getServer().getDefaultChannel().sendMessage(greeting).queue();
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
