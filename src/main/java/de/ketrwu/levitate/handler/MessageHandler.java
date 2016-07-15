package de.ketrwu.levitate.handler;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.MessageBuilder;

/**
 * MessageHandler used to define how Levitate will tell the user or console something. <br />
 * If you don't register your own, Levitate will use it's own. 
 * @author Kenneth Wussmann
 */
public interface MessageHandler {
	
	/**
	 * Send a message to player / console
	 * @param sender
	 * @param messageBuilder
	 */
	public void sendMessage(CommandSender sender, MessageBuilder messageBuilder);
			
}
