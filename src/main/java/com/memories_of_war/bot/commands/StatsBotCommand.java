package com.memories_of_war.bot.commands;

import com.memories_of_war.bot.exceptions.VVBotException;
import com.memories_of_war.bot.utils.Colors;
import com.memories_of_war.bot.utils.Emotes;
import com.memories_of_war.bot.utils.Flags;
import com.memories_of_war.bot.utils.Ranks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;
import com.memories_of_war.bot.exceptions.UserDoesNotExistException;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatsBotCommand implements IBotCommand {

    private static final Logger log = LoggerFactory.getLogger(StatsBotCommand.class);

	@Autowired
	private DiscordUserRepository dur;

	@Override
	public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		String mention = event.getAuthor().mention() + " ";
		Long discordId = event.getAuthor().getLongID();
		
		try {
			DiscordUser user = dur.findByDiscordId(discordId);
			
			// throw error if user is not defined.
			if (user == null)
				throw new UserDoesNotExistException();
			
			DiscordResources resources = user.getDiscordResources();

            IUser author = event.getAuthor();
            IGuild guild = event.getGuild();

            List<IRole> roles = author.getRolesForGuild(guild);

            String emoteAndFactionName = "factionless";
            String factionFlag = "";
            String characterRank = Ranks.LEVEL_1;
            Color factionColor = Color.BLACK;
            for (IRole role : roles) {
                switch (role.getName()) {
                    case "Shogun Empire":
                        emoteAndFactionName = Emotes.SE + " " + role.getName();
                        factionFlag = Flags.SE;
                        factionColor = Colors.SE;
                        break;
                    case "African Warlords":
                        emoteAndFactionName = Emotes.AW + " " + role.getName();
                        factionFlag = Flags.AW;
                        factionColor = Colors.AW;
                        break;
                    case "European Alliance":
                        emoteAndFactionName = Emotes.EA + " " + role.getName();
                        factionFlag = Flags.EA;
                        factionColor = Colors.EA;
                        break;
                    case "Soviet Union":
                        emoteAndFactionName = Emotes.SU + " " + role.getName();
                        factionFlag = Flags.SU;
                        factionColor = Colors.SU;
                        break;
                    case "Latin Junta":
                        emoteAndFactionName = Emotes.LJ + " " + role.getName();
                        factionFlag = Flags.LJ;
                        factionColor = Colors.LJ;
                        break;
                    case "United Republic":
                        emoteAndFactionName = Emotes.UR + " " + role.getName();
                        factionFlag = Flags.UR;
                        factionColor = Colors.UR;
                        break;
                    case "Adjutant":
                        characterRank = Ranks.LEVEL_2;
                        break;
                    case "Merc":
                        characterRank = Ranks.LEVEL_3;
                        break;

                    default:
                        // do nothing.
                }
            }

            String rolesField = String.join(
                    ", ",
                    roles.stream().filter(iRole -> !iRole.isEveryoneRole()).map(IRole::getName).collect(Collectors.toList()));

            EmbedBuilder builder = new EmbedBuilder();

            builder.withColor(factionColor);

            builder.withAuthorIcon(characterRank);
            builder.withAuthorName(user.getDiscordUsername());
            builder.withAuthorUrl(characterRank);

            builder.withThumbnail(event.getAuthor().getAvatarURL());

            builder.withTitle("Profile");
            builder.withDescription(user.getDescription());

            builder.appendField("Allegiance", emoteAndFactionName, false);

            builder.appendField("Discord roles", rolesField, false);

            builder.appendField("Gems", ":gem: " + resources.getGems() + "/1000", true);
            builder.appendField("Gems spent", ":gem: " + resources.getSpentGems(), true);

            builder.appendField("Wealth", ":moneybag: " + resources.getGold() + "/10000", true);
            builder.appendField("Wealth spent", ":moneybag: " + resources.getSpentGold(), true);

            builder.appendField("V2 Rockets launched", ":rocket: " + resources.getV2Launched().toString(), false);

            event.getChannel().sendMessage(builder.build());

        } catch (VVBotException e) {
            event.getChannel().sendMessage(mention + e.getMessage());

        } catch (Exception e) {
            event.getChannel().sendMessage(mention + " !stats command failed. Please inform the moderators.");

            String errorMessage = String.format("User %s in channel %s:", event.getAuthor().getName(), event.getChannel().getName());
            log.error(errorMessage, e);
        }
	}

	@Override
	public String getCommandName() {
		return "!stats";
	}

	@Override
	public String getCommandDescription() {
		return "Type !stats to check your statistics.";
	}

}
