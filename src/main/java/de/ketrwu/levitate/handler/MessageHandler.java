package de.ketrwu.levitate.handler;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.MessageBuilder;

public interface MessageHandler {
	
	/**
	 * Send a message to player / console
	 * @param sender
	 * @param messageBuilder
	 */
	public void sendMessage(CommandSender sender, MessageBuilder messageBuilder);
			
}
