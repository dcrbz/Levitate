package de.ketrwu.levitate;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Handler for syntax
 * @author Kenneth Wussmann
 */
public interface SyntaxHandler {
	
	/**
	 * Handles whether a value is vaild
	 * @param sender The sender which uses a command with this syntax
	 * @param parameter The parameters of the syntax
	 * @param passed The value passed by the user
	 * @throws CommandSyntaxException
	 * @throws SyntaxResponseException
	 */
	public void check(CommandSender sender, String parameter, String passed) throws CommandSyntaxException, SyntaxResponseException;
	
	/**
	 * Return every possible value for your syntax.
	 * You don't need to filter or sort it.
	 * @param sender The sender which tries to tab-complete
	 * @param parameter The parameters of the syntax
	 * @param passed The value passed by the user
	 * @return
	 */
	public List<String> getTabComplete(CommandSender sender, String parameter, String passed);
}
