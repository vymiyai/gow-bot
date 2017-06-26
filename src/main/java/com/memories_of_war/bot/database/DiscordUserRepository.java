package com.memories_of_war.bot.database;

import org.springframework.data.repository.CrudRepository;

public interface DiscordUserRepository extends CrudRepository<DiscordUser, Long> {

	DiscordUser findByDiscordUsernameIgnoreCase(String discordUsername);
	
	DiscordUser findByDiscordId(Long discordId);
}