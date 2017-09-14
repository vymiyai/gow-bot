package com.memories_of_war.bot.commands;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.NotEnoughGemsException;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;
import com.memories_of_war.bot.exceptions.VVBotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class V2BotCommand implements IBotCommand {

    public static final Integer V2_ROCKET_GEM_COST = 20;
    private static final Logger log = LoggerFactory.getLogger(V2BotCommand.class);
    private final Long GOW_BOT_ID = 318068287692865536L;

    @Autowired
    private DiscordUserRepository dur;

    private void updateUserResources(DiscordResources userResources) {
        // subtract gems from user.
        Integer gems = userResources.getGems();
        userResources.setGems(gems - V2_ROCKET_GEM_COST);

        // add gems to spent gems.
        Integer spentGems = userResources.getSpentGems();
        userResources.setSpentGems(spentGems + V2_ROCKET_GEM_COST);

        // increase V2 launched count.
        Integer v2Launched = userResources.getV2Launched();
        userResources.setV2Launched(v2Launched + 1);
    }

    private void updateTargetResources(DiscordResources targetResources) {
        // calculate time difference.
        Long cooldownTimestamp = targetResources.getCooldown().getTime();
        Long now = new Date().getTime();
        Long diff = now - cooldownTimestamp;

        // create new timestamp in miliseconds.
        Long penalty = 1L * 60 * 60 * 1000;
        Long newCooldownTimestamp;
        if (diff > 0)
            // add one hour to now timestamp.
            newCooldownTimestamp = now + penalty;
        else
            // add one hour to cooldownTimestamp.
            newCooldownTimestamp = cooldownTimestamp + penalty;

        targetResources.setCooldown(new Timestamp(newCooldownTimestamp));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
        String mention = event.getAuthor().mention() + " ";
        Long discordId = event.getAuthor().getLongID();

        try {
            DiscordUser user = dur.findByDiscordId(discordId);

            // throw error if user not found.
            if (user == null)
                throw new UserDoesNotExistException();

            DiscordResources userResources = user.getDiscordResources();

            // throw error if not enough gems for V2.
            if (userResources.getGems() < V2_ROCKET_GEM_COST)
                throw new NotEnoughGemsException(V2_ROCKET_GEM_COST);

            // get mentions in message. Throw error if none or more than one.
            List<IUser> mentions = event.getMessage().getMentions();
            if (mentions.isEmpty() || mentions.size() > 1) {
                event.getChannel().sendMessage(mention + " Choose a single @target ffs.");
                return;
            }

            // retrieve target user.
            IUser target = mentions.get(0);
            DiscordUser targetUser = dur.findByDiscordId(target.getLongID());

            // return early if the target is the bot itself.
            if (this.GOW_BOT_ID.equals(target.getLongID())) {
                event.getChannel().sendMessage(mention + " nice try scrub.");
                return;
            }

            // throw error if user doesn't exist in the database.
            if (targetUser == null) {
                event.getChannel().sendMessage(mention + " Error: Target user " + target.getName() + " is not registered.");
                return;
            }

            // update user's resources.
            this.updateUserResources(userResources);

            // update target's resources.
            DiscordResources targetResources = targetUser.getDiscordResources();
            this.updateTargetResources(targetResources);

            event.getChannel().sendMessage(mention + " launched a V2 rocket on " + target.mention() + "! Target's !raffle cooldown increased by one hour.");

        } catch (VVBotException e) {
            event.getChannel().sendMessage(mention + e.getMessage());

        } catch (Exception e) {
            event.getChannel().sendMessage(mention + " !v2 command failed. Please inform the moderators.");

            String errorMessage = String.format("User %s in channel %s:", event.getAuthor().getName(), event.getChannel().getName());
            log.error(errorMessage, e);
        }
    }

    @Override
    public String getCommandName() {
        return "!v2";
    }

    @Override
    public String getCommandDescription() {
        return "Type !v2 @someone to increase the target's cooldown by 1 hour. Costs " + V2_ROCKET_GEM_COST
                + " gems to deploy.";
    }

}
