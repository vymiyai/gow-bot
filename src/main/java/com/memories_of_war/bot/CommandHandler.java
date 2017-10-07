package com.memories_of_war.bot;

import com.memories_of_war.bot.commands.IBotCommand;
import com.memories_of_war.bot.commands.StatsBotCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;

import java.util.HashMap;
import java.util.List;

@Component
public class CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    private String welcomeMessage = "NOT USED";
    private String readyMesssage = "*VV-bot mark II is now online.*";
    private String playingText = "!help";
    private String botUserName = "VietnamVet";
    private String errorMessage = "Message could not be sent with error: \n";

    private HashMap<String, IBotCommand> basicCommands;

    @Autowired
    public void setBasicCommands(List<IBotCommand> injectedBasicCommands) {
        this.basicCommands = new HashMap<String, IBotCommand>();

        for (IBotCommand command : injectedBasicCommands)
            this.basicCommands.put(command.getCommandName(), command);
    }

    private String[] tokenize(String messageString) {

        return messageString.split(" ");
    }

    private boolean isDefinedCommand(String commandToken) {
        return this.basicCommands.containsKey(commandToken);
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {

        String messageString = event.getMessage().getContent();
        String[] tokenizedMessage = this.tokenize(messageString);
        String commandToken = tokenizedMessage[0].toLowerCase();

        if (isDefinedCommand(commandToken)) {
            IBotCommand command = this.basicCommands.get(commandToken);
            command.execute(tokenizedMessage, event);
        }

        // do nothing if there is no command match.
    }

    @EventSubscriber
    public void onUserJoined(UserJoinEvent event) {
        IUser user = event.getUser();
        IRole selectFaction = event.getGuild().getRolesByName("Select Faction").get(0);
        user.addRole(selectFaction);

        String response = String.format(this.getWelcomeMessage(), user.mention());

        try {
            event.getGuild().getGeneralChannel().sendMessage(response);
        } catch (DiscordException e) {
            log.error(this.errorMessage, e);
        }
    }

    /*
    @EventSubscriber
    public void onSelfJoined(ReadyEvent event) {
        String response = this.readyMesssage;

        event.getClient().getGuilds().forEach((guild) -> {
            try {

                IDiscordClient client = event.getClient();
                client.changeUsername(botUserName);
                client.changePlayingText(playingText);
                guild.getGeneralChannel().sendMessage(response);

            } catch (DiscordException e) {
                log.error(this.errorMessage, e);
            }
        });
    }
    */

    private String getWelcomeMessage() {
        StringBuilder response = new StringBuilder();
        response.append("%s *and ofc they cater to the non existant newbies than the vets still playing everyday. sigh*\n\n");
        response.append("Welcome to the community-managed March of War Discord server. I am VietnamVet-bot, in short, VV-bot.\n\n");

        response.append("**Please state if you have played the game before, your main faction and how found this Discord server. Failure to comply with this instruction will get you kicked from the server.**\n\n");

        response.append("```- Feel free to ask around for information about the original game's outcome, complain about the EA Spy or to get to know what the community has been doing to try to revive the game.\n\n");
        response.append("- Remember to mention to one of the moderators your main faction, so that you can gain access to the faction-specific chats. Users without a faction are regularly kicked from the server.\n\n");
        response.append("- For questions regarding the project Avant Guard or the organization Solace Workshop, please refer to the users with pink usernames or Nero. While some Solace Workshop members lurk this server regularly, the server and its moderators are not officially associated with the Avant Guard project nor Solace Workshop.\n\n");
        response.append("- For V²-bot specific commands, type !help for a list of available commands.\n\n");
        response.append("- If you know other players, be sure to tell them about us!```");

        return response.toString();
    }

}
