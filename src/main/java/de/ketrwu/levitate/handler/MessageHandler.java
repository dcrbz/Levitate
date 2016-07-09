package de.ketrwu.levitate.handler;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.bukkit.LevitateMessagePreprocessEvent;

public interface MessageHandler {
	
	/**
	 * Send a message to player / console
	 * @param sender
	 * @param message
	 */
	public void sendMessage(CommandSender sender, String message);

	/**
	 * Send a message to player / console to process the LevitateMessagePreprocessEvent
	 * @param event The preprocessed event
	 */
	public void sendMessage(LevitateMessagePreprocessEvent event);
	
	
			
}
