package de.ketrwu.levitate.exception;

/**
 * Throws when user doesn't has permission to execute the command
 * @author Kenneth Wussmann
 */
public class NoPermissionException extends Exception {
	public NoPermissionException() {
		super();
	}

	public NoPermissionException(String message) {
		super(message);
	}

	public NoPermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPermissionException(Throwable cause) {
		super(cause);
	}
}