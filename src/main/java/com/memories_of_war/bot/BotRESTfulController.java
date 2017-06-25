package com.memories_of_war.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordResourcesRepository;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;

@RestController
public class BotRESTfulController {
	// Logger.
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
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
	
	@RequestMapping("/create_test_entries")
	public String createTestEntries()
	{
		// save a couple of customers
		dur.save(new DiscordUser(11111L, "user1"));
		dur.save(new DiscordUser(22222L, "user2"));
		dur.save(new DiscordUser(33333L, "user3"));
		dur.save(new DiscordUser(44444L, "user4"));
		dur.save(new DiscordUser(55555L, "user5"));

		// fetch all customers.
		log.info("Users found with findAll():");
		log.info("-------------------------------");
		for (DiscordUser user : dur.findAll()) {
			log.info(user.toString());
		}
		log.info("");

		// fetch an individual customer by ID
		DiscordUser user = dur.findOne(4L);
		log.info("Customer found with findOne(4L):");
		log.info("--------------------------------");
		log.info(user.toString());
		log.info("");

		// fetch all resources.
		log.info("Resources found with findAll():");
		log.info("-------------------------------");
		for (DiscordResources resources : drr.findAll()) {
			log.info(resources.toString());
		}
		log.info("");

		// fetch customers by last name
		log.info("Customer found with findByUsername('user4'):");
		log.info("--------------------------------------------");
		for (DiscordUser bauer : dur.findByDiscordUsername("user4")) {
			log.info(bauer.toString());
		}
		
		return "Users created.";
	}

}