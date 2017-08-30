package com.memories_of_war.bot.commands;

import java.util.Random;

import org.springframework.stereotype.Component;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

@Component
public class VVBotCommand implements IBotCommand {

	@Override
	public String execute(String[] tokenizedMessage, MessageReceivedEvent event) {

		String[] quotes = new String[] { "Great. All my hopes destroyed AGAIN.", "Thankyou, crazy porn fanatic",
				"Oh for fuck sake.",
				"There is only one way for us to survive this invasion and that\'s for you all to work together, and follow the orders given by the High Command. So please, fight in Norway. Thank you.",
				"44-44, Come on guys. Fight in Norway!", 
				"Fucking hell.. Stop pvping you morons",
				"You\'ve already achieved your objective in advance? Oh I wonder what that was... OH I KNOW - BACKSTABBING EA AND GETTING YOUR FACTION KILLED!",
				"How the F*CK did this even get open to begin with?THIS ISNT EVEN A PORT",
				"Focus: #1 Indonesia #2 East Australia. And somebody hunt down " + event.getAuthor().mention() + " for me, dammit.",
				"Long live the Lion!",
				"Will admit now that I step back a second I see I am blaming SE for literally everything.",
				"everything\'s a big conspiracy to me now thanks to the SE.",
				"Idk what\'s real anymore :P",
				"Literally everytime EA gets painted as the faction who sends spies to cause shit.",
				"Pathetic " + event.getAuthor().mention() + ". <3",
				"Hahaha certainly did a 180 turn there " + event.getAuthor().mention() + " good job lol",
				"\"I want to be friendly\" my ass...", 
				"GET YOUR SE ALTS OFF OUR LAND",
				"I think literally everyone in the SUHC has stoped playing, otherwise this autovote massacre wouldnt still be going. Holy fucking hell",
				"Shoguns we already have a load of other bullshit to deal with, explain", 
				"FUCKING ROGUES!",
				"Is it worth me wondering what\'s happening here, or I can ignore whatever this drama is? ",
				"*printed*", 
				"ffs, really UR? After I said no to opening further on us", 
				"ROCK. ROCK. DAMNIT.",
				"Can I get the past two years of my life back please Damion? That\'d be.. nice..",
				"idk about bata but poll lovest that shit",
				"Make them pay in blood for wiping us again, the Lion demands it!",
				"Yeah go play farmville you United Backstabber.",
				"So actually they backstabbed EA yet again, which ofc they\'ll deny but whathever lol.",
				"Anyways, trying to justify saving the game is wasted on people like you " + event.getAuthor().mention() + ", who don\'t give a shit lol.",
				"Funny how you decide to act now damion, I still remember Varnicus\' 8 weeks of hell",
				"Let me guess, UR wants to kick us while we\'re down, too?", 
				"Da...fuqq...",
				"You best hope that we dont come to Asia ourselves after this ends.",
				"WE might not forget THIS ourselves.", 
				"Gangbangs dont last forever. you forget that", 
				"Carry on.",
				"<.<", 
				"And you\'re the hated little shit, " + event.getAuthor().mention() + ". Be quiet.",
				"I really hope that all other factions are watching and taking notes that SE cant be trusted at all here",
				"If anyone should get gangbanged this time, it should be SE not EA lol.", "dont jinx it^^",
				"Those were rogues", 
				"He just wanted an excuse to attack EA",
				"You guys can gangbang us all you want, but it just proves what such backstabbers you are.",
				"Note to self: Shogun Empire is no longer honorable as they claim to be",
				"Just tell the truth - was this SE\'s plan from the start? because its starting to look like it.",
				"(There\'s the point I made)", 
				"Me? On a leash? Ha! Not possible. :P", 
				"You fucking show off.",
				"I know you need money but fuck that.", 
				"Cant you just let the rogue hit them?;)", 
				"For fuck sake -.-",
				"13-4. Fucking ridiculous", 
				"I thought I told you guys to fight in Bangladesh! Get a move on!",
				"right now I want to hug my bed.. xD",
				"NO RETREAT, NO SURRENDER. FIGHT AND DIE FOR THE LION! KICK THOSE SHOGUNS OUT!",
				"Guys the fucking nightshift is over, get your lazy ass(es) up!", 
				"I do xD",
				"Can you give a better annihalation warcry?", "I\'d like to see you try. xD",
				"*randomly starts laughing*", 
				"*points at his tv showing a guy running into things*",
				"Ahem. Sorry, cant help it..", 
				"Im confused here damion lol", 
				"Same here^", 
				"you saw nothing!",
				"t(-.-)t", 
				"I am now going to report you on the suspicion of PVP exploiting. Have a good day :-D",
				"VOTE FOR VietnamVet in the EA ELECTIONS", 
				"restarting game, brb.. ffs", 
				"*sigh*",
				"*starts placing banners all over the chatroom showing off the new World Peace*",
				"he seemed to have gone to the dark side",
				"Well, thats my daily paragraph of something i'm laughing at.",
				"I mean really, what are you trying to do lol.",
				"I'd really like to know what your goal is here " + event.getAuthor().mention() + ".",
				"But it seems the logic here is that if you cant control a faction, kill them with everything else.",
				"Omg this is hilarious.",
				"And here we was hoping you would end this conflict between our two factions. You disappoint me, " + event.getAuthor().mention() + ".",
				"I call bullshit.",
				"Exactly why I saw this coming a mile away.",
				"If there's one thing i've learnt in my time playing MoW, its that I know my way around all the little hiding spots.",
				"Oh, no doubt.",
				"Im curious just how many more of you are here.",
				"Then again... if they're your successor I still have reason to believe they're corrupted by you :D",
				"Lol whatever, go back into your hole.",
				"Well, " + event.getAuthor().mention() + ".. he's a different story.",
				"The infidel thing gave it away. :)",
				"I thought it was you.",
				"I'll let you mull that over, im off to bed.",
				"Anyway im done discussing this because its going nowhere.",
				"Jeez im just speaking what i've observed for the past month",
				"you can claim whatever you want.",
				"It's not about the amount of battles you put in. It's about your reputation, and.. other stuff. lol",
				"Call me paranoid but I get the feeling you're an SE alt too " + event.getAuthor().mention() + " xD",
				"Thats a very old alt lmao^",
				"How so?",
				event.getAuthor().mention() + " wtf you talking about",
				"Where the fuck did you get that info from?",
				"So, are you done trying to bash me now? Can I go to bed?",
				"Ah, well I switched alts so I missed it.",
				"I'm attracted to drama. What's happening.",
				"Which two assholes?",
				"Naturally, I wont support this crap against EA.",
				"Buuurnnn",
				"heh thats more like it.",
				"That's not a nice way to speak to one of the most respected players in the game.",
				"You cant just do whatever you want when you have the power to start battles. It gets you hated.",
				"I wish that was true, that's a rare sight."};

		Random random = new Random();
		int index = random.nextInt(quotes.length);
		return quotes[index];
	}
	
	@Override
	public String getCommandName() {
		return "!vv";
	}
	
	@Override
	public String getCommandDescription() {
		return "Type !vv for awesomeness.";
	}

}
