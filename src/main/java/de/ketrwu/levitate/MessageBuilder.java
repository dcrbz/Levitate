package de.ketrwu.levitate;

import de.ketrwu.levitate.Message.TextMode;

import java.util.HashMap;

/**
 * Used to build messages
 *
 * @author Kenneth Wussmann
 */
public class MessageBuilder {

    private Message message;
    private TextMode textMode = TextMode.COLOR;
    private HashMap<String, String> replaces = new HashMap<String, String>();
    private String raw;


    public MessageBuilder() {
    }

    /**
     * Only used until the old messages using strings will be removed completly!
     *
     * @param message
     * @deprecated Use {@link #MessageBuilder()} instead.
     */
    @Deprecated
    public MessageBuilder(String raw) {
        this.raw = raw;
    }

    public MessageBuilder(Message message, TextMode textMode) {
        this.message = message;
        this.textMode = textMode;
    }

    public MessageBuilder(Message message, TextMode textMode, HashMap<String, String> replaces) {
        this.message = message;
        this.textMode = textMode;
        this.replaces = replaces;
    }

    /**
     * Replace a given key word with a given value. <br />
     * It'll be replaced when {@link build()} is called.<br />
     * This will not work for Raw-Messages because they are deprecated.
     *
     * @param key
     * @param value
     * @return
     */
    public MessageBuilder replace(String key, String value) {
        replaces.put(key, value);
        return this;
    }

    /**
     * Build a string with these settings
     *
     * @return Formatted and replaced message for the user
     */
    public String build() {
        if (raw != null) return raw;
        return getMessage().get(getTextMode(), getReplaces());
    }

    /**
     * Get the message of the Message-Enum
     *
     * @return
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Get the TextMode the message will be formatted in
     *
     * @return
     */
    public TextMode getTextMode() {
        return textMode;
    }

    /**
     * Get all replaces registered to this MessageBuilder
     *
     * @return
     */
    public HashMap<String, String> getReplaces() {
        return replaces;
    }

    /**
     * Set the message of the Message-Enum
     *
     * @param message
     * @return Itself, so you can append more set-Methods
     */
    public MessageBuilder setMessage(Message message) {
        this.message = message;
        return this;
    }

    /**
     * Set the TextMode used to format this message
     *
     * @param textMode
     * @return Itself, so you can append more set-Methods
     */
    public MessageBuilder setTextMode(TextMode textMode) {
        this.textMode = textMode;
        return this;
    }

    /**
     * Set the replaces. This will delete all other replaces registered before. <br />
     * Use {@link addReplaces(HashMap<String, String> replaces)} to not delete old replaces. <br />
     * Or use {@link replaces(String key, String value)} to add only one replacement.
     *
     * @param replaces
     * @return Itself, so you can append more set-Methods
     */
    public MessageBuilder setReplaces(HashMap<String, String> replaces) {
        if (replaces == null) replaces = new HashMap<String, String>();
        this.replaces = replaces;
        return this;
    }

    /**
     * Add a Map of replaces to the current replaces.
     *
     * @param replaces
     * @return Itself, so you can append more set-Methods
     */
    public MessageBuilder addReplaces(HashMap<String, String> replaces) {
        if (replaces == null) replaces = new HashMap<String, String>();
        this.replaces.putAll(replaces);
        return this;
    }


}
