package com.memories_of_war.bot.commands;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
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

import java.util.Arrays;

@Component
public class DescBotCommand implements IBotCommand {

    public static final int DESC_GEM_COST = 20;
    public static final int MAXIMUM_DESCRIPTION_LENGTH = 511;

    private static final Logger log = LoggerFactory.getLogger(DescBotCommand.class);

    @Autowired
    private DiscordUserRepository dur;

    @Override
    @Transactional
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
        String mention = event.getAuthor().mention() + " ";
        Long discordId = event.getAuthor().getLongID();

        try {
            // check for exactly at least one argument.
            if (tokenizedMessage.length < 2)
                throw new WrongNumberOfArgumentsException("The !desc command takes at least one parameter (e.g. !desc your description here).");

            // split string by first space character and return second item.
            String description = event.getMessage().getContent().split(" ", 2)[1];

            if (description.length() > MAXIMUM_DESCRIPTION_LENGTH) {
                event.getChannel().sendMessage(mention + "your description is too fucking long.");
                return;
            }

            // retrieve DiscordUser.
            DiscordUser user = this.dur.findByDiscordId(discordId);

            if (user == null)
                throw new UserDoesNotExistException();

            DiscordResources discordResources = user.getDiscordResources();
            Integer userGems = discordResources.getGems();
            Integer userSpentGems = discordResources.getSpentGems();
            if (userGems >= DESC_GEM_COST) {
                discordResources.setGems(userGems - DESC_GEM_COST);
                discordResources.setSpentGems(userSpentGems + DESC_GEM_COST);
            } else {
                throw new NotEnoughGemsException(DESC_GEM_COST);
            }

            // set character description.
            user.setDescription(description);

            event.getChannel().sendMessage(mention + "Character description updated.");

        } catch (VVBotException e) {
            event.getChannel().sendMessage(mention + e.getMessage());

        } catch (Exception e) {
            event.getChannel().sendMessage(mention + " !desc command failed. Please inform the moderators.");

            String errorMessage = String.format("User %s in channel %s:", event.getAuthor().getName(), event.getChannel().getName());
            log.error(errorMessage, e);
        }
    }

    @Override
    public String getCommandName() {
        return "!desc";
    }

    @Override
    public String getCommandDescription() {
        return "Type !desc your_description to add a description to your profile (maximum 500 characters). Costs " + DESC_GEM_COST + " gems to deploy.";
    }

}
