package com.memories_of_war.bot.gow_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

@SpringBootApplication
@ComponentScan("com.memories_of_war.bot.gow_bot")
@ComponentScan("com.memories_of_war.bot.commands")
@RestController
public class Application {

	/**
	 * Command handler instance.
	 */
	private static BasicCommandHandler basicCommandHandler;

	/**
	 * Initializes the command handler for this instance through injection.
	 * 
	 * @param bch
	 *            - the injected BasicCommandHandler instance.
	 */
	@Autowired
	public void setBasicCommandHandler(BasicCommandHandler bch) {
		basicCommandHandler = bch;
	}

	/**
	 * RESTful request handler for HTTP.
	 * 
	 * @return the list of command prefixes.
	 */
	@RequestMapping("/")
	public String home() {

		StringBuilder sb = new StringBuilder();
		for (String prefix : basicCommandHandler.getCommandPrefixes()) {
			sb.append(prefix);
			sb.append("\n");
		}

		return sb.toString();
	}

	/**
	 * Main method run statically.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// get token as environment variable.
			final String token = System.getenv("gow_token");

			SpringApplication.run(Application.class, args);

			IDiscordClient cli = new ClientBuilder().withToken(token).withRecommendedShardCount().build();

			// Register a listener via the EventSubscriber annotation which
			// allows for organization and delegation of events
			cli.getDispatcher().registerListener(basicCommandHandler);

			// Only login after all events are registered otherwise some may be
			// missed.
			cli.login();
		} catch (Exception e) {
			// do nothing.
			System.out.println("____ERROR:" + e.getMessage());
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

}
