package de.ketrwu.levitate.exception;

/**
 * Throws when something with parsing your command via @Command went wrong
 * @author Kenneth Wussmann
 */
public class SyntaxAnnotationException extends Exception {
	public SyntaxAnnotationException() {
		super();
	}

	public SyntaxAnnotationException(String message) {
		super(message);
	}

	public SyntaxAnnotationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyntaxAnnotationException(Throwable cause) {
		super(cause);
	}
}