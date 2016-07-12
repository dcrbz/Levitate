package de.ketrwu.levitate.exception;

import de.ketrwu.levitate.MessageBuilder;

/**
 * Throws by SyntaxHandler when user-input is invalid
 * @author Kenneth Wussmann
 */
public class SyntaxResponseException extends Exception {
	
	private MessageBuilder messageBuilder;
	
	public SyntaxResponseException() {
		super();
	}
	
	public SyntaxResponseException(MessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
	}
	
	/**
	 * @deprecated Use {@link #SyntaxResponseException(MessageBuilder messageBuilder)} instead.
	 * @param message
	 */
	@Deprecated
	public SyntaxResponseException(String message) {
		super(message);
	}

	public SyntaxResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyntaxResponseException(Throwable cause) {
		super(cause);
	}

	public MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
}