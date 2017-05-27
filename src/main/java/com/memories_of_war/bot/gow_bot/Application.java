package com.memories_of_war.bot.gow_bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

@SpringBootApplication
@RestController
public class Application {
	public static String token = "";

	@RequestMapping("/")
	public String home() {
		return "AMERICA FUCK YEAH";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		/*
		IDiscordClient cli = new ClientBuilder().withToken(token).withRecommendedShardCount().build();

		// Register a listener via the EventSubscriber annotation which allows
		// for organisation and delegation of events
		cli.getDispatcher().registerListener(new BasicCommandHandler());

		// Only login after all events are registered otherwise some may be
		// missed.
		cli.login();
		*/
	}
	
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> 
        {
            System.out.println(ctx.getEnvironment().getProperty("gow_token"));
        };
    }

}
