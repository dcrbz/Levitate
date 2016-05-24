package de.ketrwu.levitate.syntax;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.SyntaxHandler;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Checks if user-input is not string. Case-sensitive.
 * @author Kenneth Wussmann
 */
public class NotEqualsSyntax implements SyntaxHandler {

	@Override
	public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%arg%", passed);
		replaces.put("%value%", parameter);
		if(parameter.equals(passed)) throw new SyntaxResponseException(Message.NOTEQUALSSYNTAX_CANNOT_EQUAL.get(TextMode.COLOR, replaces));
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
		return null;
	}
}
