package com.memories_of_war.bot.commands;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordResourcesRepository;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates a new DiscordUser in the database if the following applies:
 * 1 - user has no registered character yet.
 * 2 - user name is valid.
 * 3 - user name doesn't exist yet.
 *
 * @author Darkagma
 */
@Component
public class JoinBotCommand implements IBotCommand {

    private static final Logger log = LoggerFactory.getLogger(JoinBotCommand.class);

    protected final Integer MAXIMUM_USERNAME_LENGTH = 32;

    @Autowired
    protected DiscordResourcesRepository discordResourceRepository;

    @Autowired
    protected DiscordUserRepository discordUserRepository;

    protected String pattern = "^[a-zA-Z0-9_]+$";

    // checks if the provided user name is valid - alphanumeric and underscores and at most 32 characters length.
    protected boolean isValidUsername(String username)
            throws UsernameTooLongException, UsernameWithInvalidCharactersException {
        // test length.
        if (username.length() > this.MAXIMUM_USERNAME_LENGTH)
            throw new UsernameTooLongException();

        // test for forbidden characters.
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(username);
        if (!m.matches())
            throw new UsernameWithInvalidCharactersException();

        return true;
    }

    // checks if it there is are no DiscordUser associated with a discordId.
    private boolean isFirstCharacter(Long discordId) throws UserAlreadyExistsException {
        if (this.discordUserRepository.findByDiscordId(discordId) == null)
            return true;
        else
            throw new UserAlreadyExistsException();
    }

    // Checks if the user name is free to be used.
    protected boolean isUsernameAvailable(String username) throws UsernameAlreadyExistsException {
        if (this.discordUserRepository.findByDiscordUsernameIgnoreCase(username) == null)
            return true;
        else
            throw new UsernameAlreadyExistsException();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
        String mention = event.getAuthor().mention() + " ";
        Long discordId = event.getAuthor().getLongID();

        try {
            // check for exactly one argument.
            if (tokenizedMessage.length != 2)
                throw new WrongNumberOfArgumentsException("The !join command takes exactly one parameter (e.g. !join character_name).");

            String username = tokenizedMessage[1];

            this.isValidUsername(username);
            this.isFirstCharacter(discordId);
            this.isUsernameAvailable(username);

            // create the new user.
            DiscordUser newUser = new DiscordUser(discordId, username);

            // create the resources. Saving is not required because of DiscordUser's cascade.
            DiscordResources discordResources = new DiscordResources();
            newUser.setDiscordResources(discordResources);

            // should work because of cascade.
            this.discordUserRepository.save(newUser);

            event.getChannel().sendMessage(mention + "Character " + username + " created.");

        } catch (VVBotException e) {
            event.getChannel().sendMessage(mention + e.getMessage());

        } catch (Exception e) {
            event.getChannel().sendMessage(mention + " !join command failed. Please inform the moderators.");

            String errorMessage = String.format("User %s in channel %s:", event.getAuthor().getName(), event.getChannel().getName());
            log.error(errorMessage, e);
        }
    }

    @Override
    public String getCommandName() {
        return "!join";
    }

    @Override
    public String getCommandDescription() {
        return "Type !join character_name to register your Discord user into the database (e.g. !join my_name).";
    }

}
