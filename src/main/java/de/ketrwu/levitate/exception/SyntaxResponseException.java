package de.ketrwu.levitate.exception;

/**
 * Throws by SyntaxHandler when user-input is invalid
 * @author Kenneth Wussmann
 */
public class SyntaxResponseException extends Exception {
	public SyntaxResponseException() {
		super();
	}

	public SyntaxResponseException(String message) {
		super(message);
	}

	public SyntaxResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyntaxResponseException(Throwable cause) {
		super(cause);
	}
}