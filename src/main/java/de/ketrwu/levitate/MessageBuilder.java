package de.ketrwu.levitate;

import java.util.HashMap;

import de.ketrwu.levitate.Message.TextMode;

/**
 * Used to build messages
 * @author Kenneth Wussmann
 */
public class MessageBuilder {

	private Message message;
	private TextMode textMode = TextMode.COLOR;
	private HashMap<String, String> replaces = new HashMap<String, String>();
	private String raw;
	
	
	public MessageBuilder() { }
	
	/**
	 * Only used until the old messages using strings will be removed completly!
	 * @deprecated Use {@link #MessageBuilder()} instead.
	 * @param message
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
	
	public MessageBuilder replace(String key, String value) {
		replaces.put(key, value);
		return this;
	}
	
	/**
	 * Build a string with these settings
	 * @return Formatted and replaced message for the user
	 */
	public String build() {
		if(raw != null) return raw;
		return getMessage().get(getTextMode(), getReplaces());
	}

	public Message getMessage() {
		return message;
	}

	public TextMode getTextMode() {
		return textMode;
	}

	public HashMap<String, String> getReplaces() {
		return replaces;
	}

	public MessageBuilder setMessage(Message message) {
		this.message = message;
		return this;
	}

	public MessageBuilder setTextMode(TextMode textMode) {
		this.textMode = textMode;
		return this;
	}

	public MessageBuilder setReplaces(HashMap<String, String> replaces) {
		if(replaces == null) replaces = new HashMap<String, String>();
		this.replaces = replaces;
		return this;
	}
	
	
	
}
