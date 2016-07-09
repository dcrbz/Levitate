package de.ketrwu.levitate.handler;

import org.bukkit.command.CommandSender;

public interface MessageHandler {
	
	/**
	 * Send a message to player / console
	 * @param sender
	 * @param message
	 */
	public void sendMessage(CommandSender sender, String message);
			
}
