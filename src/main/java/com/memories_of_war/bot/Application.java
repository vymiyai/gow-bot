package com.memories_of_war.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordResourcesRepository;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

@SpringBootApplication
public class Application {

	// Logger.
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	// as of 23.06.2017, moving the autowired annotation from the setter to this
	// property fucks up everything.
	private static BasicCommandHandler basicCommandHandler;

	@Autowired
	private void setBasicCommandHandler(BasicCommandHandler bch) {
		basicCommandHandler = bch;
	}

	// Discord token for GoW-bot. Same case as the CommandHandler.
	private static String GOW_TOKEN;

	@Value("${discord.gow_token}")
	private void setGowToken(String gowToken) {
		GOW_TOKEN = gowToken;
	}

	/**
	 * Main method run statically.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

		// get token as environment variable.
		final String token = GOW_TOKEN;

		try {
			IDiscordClient cli = new ClientBuilder().withToken(token).withRecommendedShardCount().build();

			// Register a listener via the EventSubscriber annotation which
			// allows for organization and delegation of events
			cli.getDispatcher().registerListener(basicCommandHandler);

			// Only login after all events are registered otherwise some may be
			// missed.
			cli.login();
		} catch (Exception e) {
			// do nothing.
			log.warn("WARNING - Discord4J :" + e.getMessage());
		}
	}

	/**
	 * Bean that lists java Beans registered by Spring.
	 * 
	 * @param ctx
	 * @return
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println();
		};
	}

	@Bean
	public CommandLineRunner demo(DiscordUserRepository dur, DiscordResourcesRepository drr) {
		return (args) -> {
			log.info("Command Line Runner is running.");
		};
	}

}
