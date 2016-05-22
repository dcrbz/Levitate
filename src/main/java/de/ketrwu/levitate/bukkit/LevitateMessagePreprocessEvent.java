package de.ketrwu.levitate.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;

/**
 * Gets called before Levitate sends a message to the user. <br />
 * When cancelled, Levitate wont send a message to the user.<br />
 * Levitate will send the message from getMessage() to the user.
 * @author Kenneth Wussmann
 */
public class LevitateMessagePreprocessEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
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
    public LevitateMessagePreprocessEvent(CommandSender receiver, Message messageType, TextMode textMode, String message) {
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

	public Message getMessageType() {
		return messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TextMode getTextMode() {
		return textMode;
	}

	public CommandSender getReceiver() {
		return receiver;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	    
}
