package de.ketrwu.levitate;

import java.util.List;

import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Handler for syntax
 * @author Kenneth Wussmann
 */
public interface SyntaxHandler {
	
	/**
	 * Handles whether a value is vaild
	 * @param parameter The parameters of the syntax
	 * @param passed The value passed by the user
	 * @throws CommandSyntaxException
	 * @throws SyntaxResponseException
	 */
	public void check(String parameter, String passed) throws CommandSyntaxException, SyntaxResponseException;
	
	/**
	 * Return every possible value for your syntax.
	 * You don't need to filter or sort it.
	 * @param parameter The parameters of the syntax
	 * @param passed The value passed by the user
	 * @return
	 */
	public List<String> getTabComplete(String parameter, String passed);
}
