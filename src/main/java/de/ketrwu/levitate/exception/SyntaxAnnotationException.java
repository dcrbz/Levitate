package de.ketrwu.levitate.exception;

import de.ketrwu.levitate.MessageBuilder;

/**
 * Throws when something with parsing your command via @Command went wrong
 *
 * @author Kenneth Wussmann
 */
public class SyntaxAnnotationException extends Exception {

    private MessageBuilder messageBuilder;

    public SyntaxAnnotationException() {
        super();
    }

    public SyntaxAnnotationException(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    /**
     * @param message
     * @deprecated Use {@link #SyntaxAnnotationException(MessageBuilder messageBuilder)} instead.
     */
    @Deprecated
    public SyntaxAnnotationException(String message) {
        super(message);
    }

    public SyntaxAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxAnnotationException(Throwable cause) {
        super(cause);
    }

    public MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

}