
package com.memories_of_war.bot.database;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DiscordResourcesRepository extends CrudRepository<DiscordResources, Long> {

	List<DiscordUser> findByDiscordUser(DiscordUser discordUser);
}