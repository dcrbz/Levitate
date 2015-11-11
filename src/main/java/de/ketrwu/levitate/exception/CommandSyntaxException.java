package de.ketrwu.levitate.exception;

/**
 * Throws when something with parsing your syntax went wrong
 * @author Kenneth Wussmann
 */
public class CommandSyntaxException extends Exception {
	public CommandSyntaxException() {
		super();
	}

	public CommandSyntaxException(String message) {
		super(message);
	}

	public CommandSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandSyntaxException(Throwable cause) {
		super(cause);
	}
}