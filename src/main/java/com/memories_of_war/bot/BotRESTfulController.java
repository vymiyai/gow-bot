package com.memories_of_war.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordResourcesRepository;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;

@RestController
public class BotRESTfulController {
	/**
	 * Command handler instance.
	 */
	@Autowired
	private CommandHandler basicCommandHandler;

	@Autowired
	private DiscordUserRepository dur;
	
	@Autowired
	private DiscordResourcesRepository drr;

	@RequestMapping("/")
	public String index() {
		StringBuilder sb = new StringBuilder();
		sb.append("Discord user entries:<br/>");
		
		for (DiscordUser user : this.dur.findAll()) {
			sb.append(user.toString());
			sb.append("<br/>");
		}
		
		sb.append("Discord resources entries:<br/>");
		for (DiscordResources user : this.drr.findAll()) {
			sb.append(user.toString());
			sb.append("<br/>");
		}
		
		sb.append("GoW-bot commands:<br/>");
		for (String prefix : this.basicCommandHandler.getCommandPrefixes()) {
			sb.append(prefix);
			sb.append("\n");
		}
		return sb.toString();
	}
}