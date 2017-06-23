package com.memories_of_war.bot.database;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class DiscordResources {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(mappedBy = "discordResources")
	private DiscordUser discordUser;

	@CreationTimestamp
	private Timestamp cooldown;

	private Integer gems;

	private Integer gold;

	private Integer spentGems;

	private Integer spentGold;

	private Integer v2Launched;

	// new user constructor.
	public DiscordResources() {
		this.gems = 0;
		this.gold = 0;
		this.spentGems = 0;
		this.spentGold = 0;
		this.v2Launched = 0;
	}

	// constructor for previous users.
	public DiscordResources(Integer gems, Integer gold, Integer spentGems, Integer spentGold, Integer v2Launched) {
		this.gems = gems;
		this.gold = gold;
		this.spentGems = spentGems;
		this.spentGold = spentGold;
		this.v2Launched = v2Launched;
	}

	@Override
	public String toString() {
		return String.format(
				"Discord user resources [id='%d', cooldown='%s', gems='%d', gold='%d', spentGems='%d', spentGold='%d', v2Launched='%d']",
				this.id, this.cooldown, this.gems, this.gold, this.spentGems, this.spentGold, this.v2Launched);
	}

	public Long getId() {
		return id;
	}

	public DiscordUser getDiscordUser() {
		return discordUser;
	}

	public void setDiscordUser(DiscordUser discordUser) {
		this.discordUser = discordUser;
	}

	public Timestamp getCooldown() {
		return cooldown;
	}

	public void setCooldown(Timestamp cooldown) {
		this.cooldown = cooldown;
	}

	public Integer getGems() {
		return gems;
	}

	public void setGems(Integer gems) {
		this.gems = gems;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getSpentGems() {
		return spentGems;
	}

	public void setSpentGems(Integer spentGems) {
		this.spentGems = spentGems;
	}

	public Integer getSpentGold() {
		return spentGold;
	}

	public void setSpentGold(Integer spentGold) {
		this.spentGold = spentGold;
	}

	public Integer getV2Launched() {
		return v2Launched;
	}

	public void setV2Launched(Integer v2Launched) {
		this.v2Launched = v2Launched;
	}

}