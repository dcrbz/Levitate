package eu.blackwoods.levitate.exception;

/**
 * Throws when something with parsing your command via @Command went wrong
 * @author Kenneth Wussmann
 */
public class CommandAnnotationException extends Exception {
	public CommandAnnotationException() {
		super();
	}

	public CommandAnnotationException(String message) {
		super(message);
	}

	public CommandAnnotationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandAnnotationException(Throwable cause) {
		super(cause);
	}
}