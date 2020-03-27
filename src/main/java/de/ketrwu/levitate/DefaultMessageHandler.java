package de.ketrwu.levitate;

import de.ketrwu.levitate.handler.MessageHandler;
import org.bukkit.command.CommandSender;

/**
 * Default MessageHandler used to send messages to player or console
 *
 * @author Kenneth Wussmann
 */
public class DefaultMessageHandler implements MessageHandler {

    @Override
    public void sendMessage(CommandSender sender, MessageBuilder messageBuilder) {
        if (sender == null) return;
        sender.sendMessage(messageBuilder.build());
    }

}
