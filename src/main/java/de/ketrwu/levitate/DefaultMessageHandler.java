package de.ketrwu.levitate;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.handler.MessageHandler;

public class DefaultMessageHandler implements MessageHandler {

	@Override
	public void sendMessage(CommandSender sender, MessageBuilder messageBuilder) {
		if(sender == null) return;
		sender.sendMessage(messageBuilder.build());
	}
	
}
