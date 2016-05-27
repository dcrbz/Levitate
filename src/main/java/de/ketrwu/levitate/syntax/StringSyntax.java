package de.ketrwu.levitate.syntax;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;

/**
 * Checks if user-input is a string
 * @author Kenneth Wussmann
 */
public class StringSyntax implements SyntaxHandler {

	@Override
	public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%arg%", passed);
		if(parameter.equals("") || parameter.equals("aA")) {
			if(isInt(passed)) throw new SyntaxResponseException(Message.STRINGSYNTAX_CANNOT_BE_INT.get(TextMode.COLOR, replaces));
			return;
		}
		if(parameter.equals("a")) {
			if(!isLowerCase(passed)) throw new SyntaxResponseException(Message.STRINGSYNTAX_ONLY_LOWERCASE.get(TextMode.COLOR, replaces));
		}
		if(parameter.equals("A")) {
			if(!isUpperCase(passed)) throw new SyntaxResponseException(Message.STRINGSYNTAX_ONLY_UPPERCASE.get(TextMode.COLOR, replaces));
		}
		return;
	}

	public boolean isInt(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) { }
		return false;
	}

	public boolean isLowerCase(String input) {
		for(char ch : input.toCharArray()) {
			String c = String.valueOf(ch);
			if(!c.equals(c.toLowerCase())) return false;
		}
		return true;
	}

	public boolean isUpperCase(String input) {
		for(char ch : input.toCharArray()) {
			String c = String.valueOf(ch);
			if(!c.equals(c.toUpperCase())) return false;
		}
		return true;
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
		return null;
	}
	
}
