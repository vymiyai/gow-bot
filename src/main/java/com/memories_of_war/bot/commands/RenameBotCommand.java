package com.memories_of_war.bot.commands;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordResourcesRepository;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.exceptions.NotEnoughGemsException;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;
import com.memories_of_war.bot.exceptions.VVBotException;
import com.memories_of_war.bot.exceptions.WrongNumberOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class RenameBotCommand extends JoinBotCommand implements IBotCommand {

    public static final Integer RENAME_GEM_COST = 100;
    private static final Logger log = LoggerFactory.getLogger(RenameBotCommand.class);
    @Autowired
    private DiscordResourcesRepository drr;

    @Override
    @Transactional
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
        String mention = event.getAuthor().mention() + " ";
        Long discordId = event.getAuthor().getLongID();

        try {
            // check for exactly one argument.
            if (tokenizedMessage.length != 2)
                throw new WrongNumberOfArgumentsException("The !rename command takes exactly one parameter (e.g. !rename character_name).");


            String username = tokenizedMessage[1];

            this.isValidUsername(username);
            this.isUsernameAvailable(username);

            // retrieve DiscordUser.
            DiscordUser user = this.discordUserRepository.findByDiscordId(discordId);

            if (user == null)
                throw new UserDoesNotExistException();

            DiscordResources discordResources = user.getDiscordResources();
            Integer userGems = discordResources.getGems();
            Integer userSpentGems = discordResources.getSpentGems();
            if (userGems >= RENAME_GEM_COST) {
                discordResources.setGems(userGems - RENAME_GEM_COST);
                discordResources.setSpentGems(userSpentGems + RENAME_GEM_COST);
            } else {
                throw new NotEnoughGemsException(RENAME_GEM_COST);
            }

            // get old username.
            String oldUsername = user.getDiscordUsername();

            // rename character.
            user.setDiscordUsername(username);

            event.getChannel().sendMessage(mention + "Character " + oldUsername + " successfully renamed to " + username + ".");

        } catch (VVBotException e) {
            event.getChannel().sendMessage(mention + e.getMessage());

        } catch (Exception e) {
            event.getChannel().sendMessage(mention + " !rename command failed. Please inform the moderators.");

            String errorMessage = String.format("User %s in channel %s:", event.getAuthor().getName(), event.getChannel().getName());
            log.error(errorMessage, e);
        }
    }

    @Override
    public String getCommandName() {
        return "!rename";
    }

    @Override
    public String getCommandDescription() {
        return "Type !rename character_name to change your character name. Costs 100 gems to deploy.";
    }

}
