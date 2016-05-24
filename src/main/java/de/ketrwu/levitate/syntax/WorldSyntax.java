package de.ketrwu.levitate.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.SyntaxHandler;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Checks if user-input is a worldname
 * @author Kenneth Wussmann
 */
public class WorldSyntax implements SyntaxHandler {

	@Override
	public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException {
		World w = Bukkit.getWorld(passed);
		if(w == null) {
			HashMap<String, String> replaces = new HashMap<String, String>();
			replaces.put("%world%", passed);
			throw new SyntaxResponseException(Message.WORLDSYNTAX_WORLD_DOES_NOT_EXIST.get(TextMode.COLOR, replaces));
		}
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
		List<String> complete = new ArrayList<String>();
		for(World w : Bukkit.getWorlds())
			complete.add(w.getName());
		return complete;
	}
}
