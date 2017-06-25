package com.memories_of_war.bot.commands;

import java.util.TreeMap;

import org.springframework.stereotype.Component;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class GlossaryBotCommand implements IBotCommand {

	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {
		
		// create dictionary.
		TreeMap<String, String> dictionary = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		dictionary.put("AW", "AW - African Warlords, identified by the color yellow and flag " + Emotes.AW);
		dictionary.put("SE", "SE - Shogun Empire, identified by the color white or gray and flag " + Emotes.SE);
		dictionary.put("EA", "EA - European Alliance, identified by the color purple or dark blue and flag " + Emotes.EA);
		dictionary.put("UR", "UR - United Republic, identified by the color light blue or cyan and flag " + Emotes.UR);
		dictionary.put("SU", "SU - Soviet Union, identified by the color red and flag " + Emotes.SU);
		dictionary.put("LJ", "LJ - Latin Junta, identified by the color green and flag " + Emotes.LJ);
		dictionary.put("MoW", "MoW - March of War, the name of the game.");
		dictionary.put("HC", "HC - High Command, the top 10% active users of a faction who could vote to attack territories.");
		dictionary.put("FL", "FL - Faction leader, the leader of a faction elected by the majority of votes every 2 weeks.");
		dictionary.put("PT", "PT - Peace Treaty, a game mechanism that prevented two factions from opening attack fronts on each other.");
		dictionary.put("HS", "HS - Hate Speech, a game mechanism that increased BP output in a front by 10%.");
		dictionary.put("BG", "BG - Battle group, equivalent to clans in MMORPGs.");
		dictionary.put("BP", "BP - Battle points, the unit of measure that determines how long a battle front will be open.");
		dictionary.put("RP", "RP - Research points, the currency used to advance the player's tech tree.");
		dictionary.put("MP", "MP - Man Power, the common name for the resource required to deploy infantry units.");
		dictionary.put("Gears", "Gears, Cogs - The common names of the resource required to deploy mechanical units.");
		dictionary.put("Cogs", "Gears, Cogs - The common names of the resource required to deploy mechanical units.");
		dictionary.put("Stars", "Stars, Command Points - The common names of the resources required to deploy Command Cards.");
		dictionary.put("Command Points", "Stars, Command Points - The common names of the resources required to deploy Command Cards.");
		dictionary.put("CC", "CC - Command Card, the single-use deployable power used in battles.");
		dictionary.put("Port", "Port - Represented by an anchor, it is a territory status that establishes a link to other ports. Ports can attack other ports independently of the distance.");
		dictionary.put("Main", "Main - Usually, the first character a player creates. Normally determines the faction allegiance of the player.");
		dictionary.put("Alt", "Alt, Toon - A secondary character created by a player who might serve another faction for the interests of the Main's faction.");
		dictionary.put("Toon", "Alt, Toon - A secondary character created by a player who might serve another faction for the interests of the Main's faction.");
		dictionary.put("Rogue", "Rogue, Rouge - An alt who blatantly defies orders and fights where they are not supposed to or votes in undesirable territories with the objective of creating distractions or waste territory votes.");
		dictionary.put("Rouge", "Rogue, Rouge - An alt who blatantly defies orders and fights where they are not supposed to or votes in undesirable territories with the objective of creating distractions or waste territory votes.");
		dictionary.put("MotD", "MotD - Message of The Day, the text block reserved for the Faction Leader to issue instructions or general purpose messages to their faction");
		dictionary.put("ISOTX", "ISOTX - The company who developed March of War and went bankrupt, taking the game down with it on February 1st 2016.");
		dictionary.put("Merc", "Merc - ISOTX's employees with admin powers, identified by the prefix \"merc-\" in their user names.");
		dictionary.put("Backstab", "Backstab - When a faction does something against another faction's interests, usually an invasion or cooperating with their enemy. Normally happened between (supposedly) friendly factions.");
		dictionary.put("Node", "Node, Spawn Point - The circular area where units can be deployed in the tactical level.");
		dictionary.put("Spawn Point", "Node, Spawn Point - The circular area where units can be deployed in the tactical level.");
		dictionary.put("Overworld", "Overworld - The world map where commanders can see territory ownerships and open fronts in a strategic level.");
		dictionary.put("Assault", "Assault - Game mode whose objective is to capture all nodes in the map. Resources are obtained by capturing those nodes. This was the default game mode.");
		dictionary.put("Blitz", "Blitz - Game mode whose objective is to destroy the enemy's Command Vehicle. Starting resources were high with few resource nodes to capture.");
		dictionary.put("CV", "CV - Command Vehicle, a special mechanical unit. A player losing it in a Blitz match or in StormSiege missions loses the match immediately.");
		dictionary.put("Siege", "Siege - Game mode whose objective was to endure 9 rounds protecting an immobile command center from AI-controlled waves of enemies.");
		dictionary.put("SS", "SS - StormSiege, previously named \"Mystery Island\". It was the last official MoW episode focusing on a single player campaign. The 3rd and last mission was never released.");
		dictionary.put("TEM", "TEM - Temperate, the kind of maps often available in the territories located on temperate zones. The majority of the territories in the northern hemisphere factions have TEM maps.");
		dictionary.put("JGL", "JGL - Jungle, the type of maps common in tropical/equatorial zones associated with rainforests. LJ and SE had large amounts of JGL maps.");
		dictionary.put("ARI", "ARI - Arid, maps associated with arid and desert climates. AW had large amounts of ARI maps. They were also present in territories like Patagonia, India, Italy and the Australias.");
		dictionary.put("VV", "VV - VietnamVet, an (in)famous EA HC, mascot of the March of War community. http://marchofwar.wikia.com/wiki/The_Rock_Of_The_European_Alliance");
		dictionary.put("Oldy", "Oldy, Oldie - A player who has played MoW since day one or at least before the player who mentioned it.");
		dictionary.put("Oldie", "Oldy, Oldie - A player who has played MoW since day one or at least before the player who mentioned it.");
		dictionary.put("WL", "WL - World Leaders chat, the chat reserved for High Commanders and Faction Leaders.");

		String mention = event.getAuthor().mention() + " ";

		// if there were no arguments, return the list of existing terms.
		if (tokenizedMessage.length == 1) {
			StringBuilder sb = new StringBuilder();
			sb.append(mention);
			sb.append("```!glossary <term>\n\n");
			sb.append("Terms defined in the glossary:\n\n");
			
			String terms = String.join(", ", dictionary.keySet());
			sb.append(terms);
			sb.append("```");
			return sb.toString();
		}

		// reassemble the searched term.
		String[] term = new String[tokenizedMessage.length - 1];
		for (int i = 1; i < tokenizedMessage.length; i++) {
			term[i - 1] = tokenizedMessage[i];
		}
		String key = String.join(" ", term);
		String definition = dictionary.get(key);
		if (definition == null)
			return mention + " Error: The term \"" + key + "\" is not defined. Type !glossary for the list of defined terms.";
		else
			return definition;
	}

	@Override
	public String getCommandName() {
		return "!glossary";
	}

	@Override
	public String getCommandDescription() {
		return "Type !glossary <term> for a brief explanation of its meaning (e.g. !glossary MotD).";
	}

}
