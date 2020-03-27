package de.ketrwu.levitate.exception;

import de.ketrwu.levitate.MessageBuilder;

/**
 * Throws when user doesn't has permission to execute the command
 *
 * @author Kenneth Wussmann
 */
public class NoPermissionException extends Exception {

    private MessageBuilder messageBuilder;

    public NoPermissionException() {
        super();
    }

    public NoPermissionException(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    /**
     * @param message
     * @deprecated Use {@link #NoPermissionException(MessageBuilder messageBuilder)} instead.
     */
    @Deprecated
    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
    }

    public MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

}