package de.ketrwu.levitate.exception;

import de.ketrwu.levitate.MessageBuilder;

/**
 * Throws when command-executor is unexpected and incompatible
 * @author Kenneth Wussmann
 */
public class ExecutorIncompatibleException extends Exception {
	
	private MessageBuilder messageBuilder;
	
	public ExecutorIncompatibleException() {
		super();
	}
	
	public ExecutorIncompatibleException(MessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
	}

	/**
	 * @deprecated Use {@link #ExecutorIncompatibleException(MessageBuilder messageBuilder)} instead.
	 * @param message
	 */
	@Deprecated
	public ExecutorIncompatibleException(String message) {
		super(message);
	}

	public ExecutorIncompatibleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExecutorIncompatibleException(Throwable cause) {
		super(cause);
	}

	public MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
}