package com.memories_of_war.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.memories_of_war.bot.database.DiscordResources;
import com.memories_of_war.bot.database.DiscordUser;
import com.memories_of_war.bot.database.DiscordUserRepository;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

@SpringBootApplication
public class Application {

	// Logger.
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	// as of 23.06.2017, moving the autowired annotation from the setter to this property fucks up everything.
	private static BasicCommandHandler basicCommandHandler;

	@Autowired
	private void setBasicCommandHandler(BasicCommandHandler bch) {
		basicCommandHandler = bch;
	}

	/**
	 * Main method run statically.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// get token as environment variable.
		final String token = System.getenv("GOW_TOKEN");

		SpringApplication.run(Application.class, args);

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
	public CommandLineRunner demo(DiscordUserRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new DiscordUser(11111L, "user1"));
			repository.save(new DiscordUser(22222L, "user2"));
			repository.save(new DiscordUser(33333L, "user3"));
			repository.save(new DiscordUser(44444L, "user4"));
			repository.save(new DiscordUser(55555L, "user5"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (DiscordUser user : repository.findAll()) {
				log.info(user.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			DiscordUser customer = repository.findOne(4L);
			log.info("Customer found with findOne(4L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// assign resources to customer.
			customer.setDiscordResources(new DiscordResources());
			repository.save(customer);

			// fetch customers by last name
			log.info("Customer found with findByUsername('user4'):");
			log.info("--------------------------------------------");
			for (DiscordUser bauer : repository.findByDiscordUsername("user4")) {
				log.info(bauer.toString());
				log.info(bauer.getDiscordResources().toString());
			}
			log.info("");
		};
	}

}
