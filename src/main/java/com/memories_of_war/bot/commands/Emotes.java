package com.memories_of_war.bot.commands;

/**
 * Returns the representation of a Discord emote.
 * Specific to the March of War server owned by Colexiel.
 * @author Darkagma
 *
 */
public enum Emotes {

	// For Discord emote ID:
	// https://www.reddit.com/r/discordapp/comments/5cce2w/how_to_get_bot_to_use_custom_emotes/
	UR("<:UR:287330770216026112>"), AW("<:AW:287330653723164672>"), LJ("<:LJ:287330907893923840>"), SE(
			"<:SE:287329412075225088>"), EA("<:EA:287330807918493706>"), SU("<:SU:287330869897854976>");

	private final String id;

	private Emotes(final String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}
