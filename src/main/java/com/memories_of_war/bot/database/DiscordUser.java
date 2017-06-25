package com.memories_of_war.bot.database;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(indexes = { @Index(name = "UK_discord_user_discord_id", columnList = "discordId"),
		@Index(name = "UK_discord_user_discord_username", columnList = "discordUsername") })
public class DiscordUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private Long discordId;

	@Column(unique = true)
	private String discordUsername;

	private String accessToken;

	@CreationTimestamp
	private Timestamp creationDate;

	@UpdateTimestamp
	private Timestamp lastChange;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "discord_resources_fk")
	private DiscordResources discordResources;

	protected DiscordUser() {
	}

	public DiscordUser(Long discordId, String discordUsername) {
		this.discordId = discordId;
		this.discordUsername = discordUsername;
	}

	@Override
	public String toString() {
		return String.format(
				"Discord user [id='%d', discordId='%d', discordUsername='%s', creationDate='%s', lastChange='%s', accessToken='%s', resources='%s']",
				this.id, this.discordId, this.discordUsername, this.creationDate, this.lastChange, this.accessToken, discordResources);
	}

	public Long getDiscordId() {
		return discordId;
	}

	public void setDiscordId(Long discordId) {
		this.discordId = discordId;
	}

	public String getDiscordUsername() {
		return discordUsername;
	}

	public void setDiscordUsername(String discordUsername) {
		this.discordUsername = discordUsername;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastChange() {
		return lastChange;
	}

	public DiscordResources getDiscordResources() {
		return discordResources;
	}

	public void setDiscordResources(DiscordResources discordResources) {
		this.discordResources = discordResources;
	}

	public Long getId() {
		return id;
	}

}