package com.memories_of_war.bot.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories_of_war.bot.BasicCommandHandler;

@RestController
public class HomeController {
	
	/**
	 * Command handler instance.
	 */
	@Autowired
	private BasicCommandHandler basicCommandHandler;

	@Autowired
	private DiscordUserRepository repository;

	@RequestMapping("/")
	public String index() {
		StringBuilder sb = new StringBuilder();
		sb.append("Database entries:<br/>");
		
		for (DiscordUser user : this.repository.findAll()) {
			sb.append(user.toString());
			sb.append("<br/>");
		}
		
		for (String prefix : this.basicCommandHandler.getCommandPrefixes()) {
			sb.append(prefix);
			sb.append("\n");
		}
		return sb.toString();
	}

}