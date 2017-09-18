package com.memories_of_war.bot.database;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(indexes = {@Index(name = "UK_discord_user_discord_id", columnList = "discordId"),
        @Index(name = "UK_discord_user_discord_username", columnList = "discordUsername")})
public class DiscordUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long discordId;

    @Column(unique = true)
    private String discordUsername;

    private String accessToken;

    private String description;

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

    public String getDescription() { return (this.description == null) ? "Type !desc your_description to update your profile description (maximum 500 characters). Requires 20 gems to deploy." : description; }

    public void setDescription(String description) { this.description = description; }


}