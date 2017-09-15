package com.memories_of_war.bot.commands;

import com.memories_of_war.bot.exceptions.VVBotException;
import com.memories_of_war.bot.exceptions.WrongNumberOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

@Component
public class QuoteBotCommand implements IBotCommand {

    private static final Logger log = LoggerFactory.getLogger(QuoteBotCommand.class);

    @Override
    public void execute(String[] tokenizedMessage, MessageReceivedEvent event) {

        String mention = event.getAuthor().mention();
        VVBotException exception = new VVBotException(mention + " I can't find the bloody message.");

        try {

            // check for at least two arguments.
            if (tokenizedMessage.length != 2)
                throw new WrongNumberOfArgumentsException("The !quote command takes exactly two arguments (e.g. !quote 1234567890).");

            String messageID = tokenizedMessage[1];

            IMessage message = event.getGuild().getMessageByID(Long.valueOf(messageID));
            if (message == null)
                throw exception;

            String response = "```| " + message.getAuthor().getDisplayName(event.getGuild()) + " said:\n| \n";

            // message exists. Quote it.
            String quotedMessageContent = message.getContent();
            response += "| " + quotedMessageContent.replace("`", "").replace("\n", "\n| ");
            response += "```\n";

            event.getChannel().sendMessage(response);

        } catch (VVBotException e) {
            event.getChannel().sendMessage(e.getMessage());

        } catch (NumberFormatException e) {
            event.getChannel().sendMessage(exception.getMessage());

        } catch (Exception e) {
            event.getChannel().sendMessage(mention + " !quote command failed. Please inform the moderators.");

            String errorMessage = String.format("User %s in channel %s:", event.getAuthor().getName(), event.getChannel().getName());
            log.error(errorMessage, e);
        }
    }


    @Override
    public String getCommandName() {
        return "!quote";
    }

    @Override
    public String getCommandDescription() {
        return "Type !quote message_id to quote a message.";
    }

}
