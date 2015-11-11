package de.ketrwu.levitate.syntax;

import java.util.List;

import de.ketrwu.levitate.SyntaxHandler;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Checks if user-input is anything
 * @author Kenneth Wussmann
 */
public class WildcardSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException {
		
	}

	@Override
	public List<String> getTabComplete(String parameter, String passed) {
		return null;
	}
}
