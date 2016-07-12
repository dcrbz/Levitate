package de.ketrwu.levitate.exception;

import de.ketrwu.levitate.MessageBuilder;

/**
 * Throws when something with parsing your syntax went wrong
 * @author Kenneth Wussmann
 */
public class CommandSyntaxException extends Exception {

	private MessageBuilder messageBuilder;
	
	public CommandSyntaxException() {
		super();
	}

	public CommandSyntaxException(MessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
	}

	/**
	 * @deprecated Use {@link #CommandSyntaxException(MessageBuilder messageBuilder)} instead.
	 * @param message
	 */
	@Deprecated
	public CommandSyntaxException(String message) {
		super(message);
	}

	public CommandSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandSyntaxException(Throwable cause) {
		super(cause);
	}
	
	public MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
}