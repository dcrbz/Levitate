package de.ketrwu.levitate.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import de.ketrwu.levitate.Levitate;
import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;

/**
 * Gets called before Levitate sends a message to the user. <br />
 * When cancelled, Levitate wont send a message to the user.<br />
 * Levitate will send the message from getMessage() to the user.
 * 
 * @author Kenneth Wussmann
 */
public class LevitateMessagePreprocessEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Plugin plugin;
	private CommandSender receiver;
	private Message messageType;
	private TextMode textMode;
	private String message;
	private boolean cancelled;

	/**
	 * Gets called before Levitate sends a message to the user. <br />
	 * When cancelled, Levitate wont send a message to the user.<br />
	 * Levitate will send the message from getMessage() to the user.
	 */
	public LevitateMessagePreprocessEvent(Plugin plugin, CommandSender receiver, Message messageType, TextMode textMode,
			String message) {
		this.plugin = plugin;
		this.receiver = receiver;
		this.messageType = messageType;
		this.textMode = textMode;
		this.message = message;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * Get the MessageType of the message
	 * @return Entry in Message-Enum
	 */
	public Message getMessageType() {
		return messageType;
	}
	
	/**
	 * Get the final message with replaced values
	 * @return Final message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message to send
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Get the styling mode of the message
	 * @return Styling mode
	 */
	public TextMode getTextMode() {
		return textMode;
	}
	
	/**
	 * Get the receiver of the message. Console or player
	 * @return
	 */
	public CommandSender getReceiver() {
		return receiver;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Cancel the message sending
	 * @param cancelled
	 */
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	/**
	 * Get the Plugin instance of the plugin which triggered this event
	 * @return
	 */
	public Plugin getPlugin() {
		return plugin;
	}	

}
