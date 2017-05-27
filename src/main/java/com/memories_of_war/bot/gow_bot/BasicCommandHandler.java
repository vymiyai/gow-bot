package com.memories_of_war.bot.gow_bot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

public class BasicCommandHandler 
{	
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) 
    {
    	// send a message back.
    	RequestBuffer.request(() -> 
    	{
            try
            {
                event.getChannel().sendMessage( ":UR:" );
            } 
            catch (DiscordException e)
            {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
    	});
    }
	
}
