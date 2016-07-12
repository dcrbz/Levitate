package de.ketrwu.levitate.exception;

import de.ketrwu.levitate.MessageBuilder;

/**
 * Throws when something with parsing your command via @Command went wrong
 * @author Kenneth Wussmann
 */
public class CommandAnnotationException extends Exception {

	private MessageBuilder messageBuilder;
	
	public CommandAnnotationException() {
		super();
	}
	
	public CommandAnnotationException(MessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
	}

	/**
	 * @deprecated Use {@link #SyntaxResponseException(MessageBuilder messageBuilder)} instead.
	 * @param message
	 */
//	public CommandAnnotationException(String message) {
//		super(message);
//	}

	public CommandAnnotationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandAnnotationException(Throwable cause) {
		super(cause);
	}
	
	public MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
}