package com.memories_of_war.bot.commands;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;
import com.memories_of_war.bot.exceptions.VVBotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

@Component
public class RaffleBotCommand implements IBotCommand {

    private static final Logger log = LoggerFactory.getLogger(RaffleBotCommand.class);

    private final Integer MAXIMUM_NUMBER_OF_GEMS = 1000;

    @Autowired
    private DiscordUserRepository dur;

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

            DiscordResources resources = user.getDiscordResources();

            if (resources.getGems().equals(this.MAXIMUM_NUMBER_OF_GEMS)) {
                event.getChannel().sendMessage(mention + "maximum number of gems reached (" + this.MAXIMUM_NUMBER_OF_GEMS + " :gem:).");
                return;
            }

            // calculate time difference.
            Long cooldownTimestamp = resources.getCooldown().getTime();
            Long now = new Date().getTime();
            Long diff = now - cooldownTimestamp;

            Long penalty;
            Long newCooldownTimestamp;
            String response;
            if (diff > 0) {
                // raffled after cooldown.

                // award gems.
                Random random = new Random();
                Integer gems = random.nextInt(10) + 1;

                if (resources.getGems() + gems > this.MAXIMUM_NUMBER_OF_GEMS)
                    resources.setGems(this.MAXIMUM_NUMBER_OF_GEMS);
                else
                    resources.setGems(resources.getGems() + gems);

                // calculate cooldown increment.
                penalty = 2L * 60 * 60 * 1000;
                newCooldownTimestamp = now + penalty;
                response = "found " + gems + " :gem:! You must wait for 2 hours before you try again.";
            } else {
                // raffled before cooldown. increment 1 hours.

                // calculate cooldown increment.
                penalty = 1L * 60 * 60 * 1000;
                newCooldownTimestamp = cooldownTimestamp + penalty;

                // calculate time for response.
                Long timeInterval = Math.abs(diff) + penalty;
                Integer seconds = (int) (timeInterval / 1000) % 60;
                Integer minutes = (int) ((timeInterval / (1000 * 60)) % 60);
                Integer hours = (int) ((timeInterval / (1000 * 60 * 60)) % 24);
                Integer days = (int) (timeInterval / (1000 * 60 * 60 * 24));

                response = "You didn't rest properly and strained your body. You must rest for one extra hour! You can try again in";

                if (days == 1)
                    response += " 1 day";

                if (days > 1)
                    response += " " + days + " days";

                if (hours == 1)
                    response += " 1 hour";

                if (hours > 1)
                    response += " " + hours + " hours";

                if (minutes == 1)
                    response += " 1 minute";

                if (minutes > 1)
                    response += " " + minutes + " minutes";

                if (seconds == 1)
                    response += " 1 second";

                if (seconds > 1)
                    response += " " + seconds + " seconds";

                response += ".";
            }

            resources.setCooldown(new Timestamp(newCooldownTimestamp));

            event.getChannel().sendMessage(mention + response);

        } catch (VVBotException e) {
            event.getChannel().sendMessage(mention + e.getMessage());

        } catch (Exception e) {
            event.getChannel().sendMessage("!raffle command failed. Please inform the moderators.");

            String errorMessage = String.format("User %s in channel %s:", event.getAuthor().getName(), event.getChannel().getName());
            log.error(errorMessage, e);
        }
    }

    @Override
    public String getCommandName() {
        return "!raffle";
    }

    @Override
    public String getCommandDescription() {
        return "Type !raffle to earn gems! (2 hours cooldown. Use with moderation)";
    }

}
