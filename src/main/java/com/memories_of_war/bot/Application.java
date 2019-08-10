package com.memories_of_war.bot;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.security.auth.login.LoginException;

@EnableScheduling
@SpringBootApplication
public class Application {

    public static final long SERVER_ID = 232501335147151360L;
    public static final long VV_ROLE_ID = 243403456692486154L;
    public static final long BOT_ROOM = 609484527919955981L;
    public static final long WORLD_CHAT = 232501335147151360L;
    public static final long ASK_SOLACE_WORKSHOP = 314214749510434816L;
    public static final long RULES = 334424603386576896L;

    // Logger.
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    // Discord token for GoW-bot. Same case as the CommandHandler.
    private static String BOT_TOKEN;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${discord.botToken}")
    private void setGowToken(String botToken) {
        BOT_TOKEN = botToken;
    }

    @Bean
    public CommandLineRunner run(BotListener botListener) {
        return args -> {
            JDA jda = new JDABuilder(BOT_TOKEN)
                    .addEventListener(botListener)
                    .build();

            // optionally block until JDA is ready
            jda.awaitReady();
        };
    }

}
