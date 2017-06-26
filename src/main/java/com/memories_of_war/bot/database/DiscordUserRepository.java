package com.memories_of_war.bot.database;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DiscordUserRepository extends CrudRepository<DiscordUser, Long> {

	List<DiscordUser> findByDiscordUsernameIgnoreCase(String discordUsername);
	
	List<DiscordUser> findByDiscordId(Long discordId);
}