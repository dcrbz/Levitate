package de.ketrwu.levitate;

import org.bukkit.command.CommandSender;

/**
 * Handles command execution when command doesn't exist
 * @author Kenneth Wussmann
 */
public interface HelpMap {
	
	/**
	 * Called when player passes invalid command
	 * @param sender CommandSender
	 * @param command Entered base of the command
	 * @param args Arguments of the command
	 */
	public void onHelp(CommandSender sender, String command, String[] args);
	
}
