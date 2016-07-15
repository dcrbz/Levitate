package de.ketrwu.levitate.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.MessageBuilder;

/**
 * Gets called before Levitate sends a message to the user. <br />
 * When cancelled, Levitate wont send a message to the user.<br />
 * Levitate will send the message builded by the MessageBuilder to the user.
 * 
 * @author Kenneth Wussmann
 */
public class LevitateMessagePreprocessEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private Plugin plugin;
	private CommandSender receiver;
	private MessageBuilder messageBuilder;

	/**
	 * Gets called before Levitate sends a message to the user. <br />
	 * When cancelled, Levitate wont send a message to the user.<br />
	 * Levitate will send the message from getMessage() to the user.
	 */
	public LevitateMessagePreprocessEvent(Plugin plugin, CommandSender receiver, MessageBuilder messageBuilder) {
		this.plugin = plugin;
		this.receiver = receiver;
		this.messageBuilder = messageBuilder;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * Get the MessageBuilder for this message
	 * @return
	 */
	public MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
	/**
	 * Only used until the old messages using strings will be removed completly!
	 * @deprecated Don't use it! Just change the settings in the existing MessageBuilder!
	 * @param message
	 */
	public MessageBuilder setMessageBuilder(MessageBuilder messageBuilder) {
		return messageBuilder;
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
	
	/**
	 * Build a message with the MessageBuilder
	 * @return Formatted and replaced message for the user
	 */
	public String getMessage() {
		return getMessageBuilder().build();
	}
	
	/**
	 * @deprecated Build your message with the MessageBuilder!
	 * @param message
	 */
	@Deprecated
	public void setMessage(String message) {
		setMessageBuilder(new MessageBuilder(message));
	}

	/**
	 * @deprecated Please use the MessageBuilder!
	 */
	@Deprecated
	public TextMode getTextMode() {
		return getMessageBuilder().getTextMode();
	}
	
	/**
	 * @deprecated Please use the MessageBuilder!
	 */
	@Deprecated
	public Message getMessageType() {
		return getMessageBuilder().getMessage();
	}


}
