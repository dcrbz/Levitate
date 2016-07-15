package de.ketrwu.levitate.syntax;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;

/**
 * Checks if user-input is anything
 * @author Kenneth Wussmann
 */
public class WildcardSyntax implements SyntaxHandler {

	@Override
	public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException {
		
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
		return null;
	}
}
