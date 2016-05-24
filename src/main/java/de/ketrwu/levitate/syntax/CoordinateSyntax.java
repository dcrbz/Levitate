package de.ketrwu.levitate.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.SyntaxHandler;
import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Used for Tab-Complete coordinates of the block a player is looking at
 * @author Kenneth Wussmann
 */
public class CoordinateSyntax implements SyntaxHandler {

	@Override
	public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%parameter%", passed);
		replaces.put("%arg%", passed);
		if(!(sender instanceof Player)) throw new CommandSyntaxException(Message.COORDSYNTAX_CONSOLE_COMMAND.get(TextMode.COLOR, replaces));
		if(parameter.trim().equals("")) throw new CommandSyntaxException(Message.COORDSYNTAX_PARAMETER_MALFORMED.get(TextMode.COLOR, replaces));
		switch(parameter.trim().toLowerCase()) {
		case "world":
			for(World w : Bukkit.getWorlds()) {
				if(w.getName().equalsIgnoreCase(passed)) return;
			}
			throw new CommandSyntaxException(Message.COORDSYNTAX_HAS_TO_BE_WORLD.get(TextMode.COLOR, replaces));
		case "x":
		case "y":
		case "z":
			if(!isInt(passed)) throw new CommandSyntaxException(Message.COORDSYNTAX_HAS_TO_BE_INTEGER.get(TextMode.COLOR, replaces));
			break;
		default:
			throw new CommandSyntaxException(Message.COORDSYNTAX_PARAMETER_MALFORMED.get(TextMode.COLOR, replaces)); 
		}
	}

	public boolean isInt(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) { }
		return false;
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
		List<String> complete = new ArrayList<String>();
		if(!(sender instanceof Player)) return complete;
		Player p = (Player) sender;
		Block b = p.getTargetBlock((HashSet<Byte>) null, 5);
		switch(parameter.trim().toLowerCase()) {
		case "world":
			complete.add(p.getWorld().getName());
			break;
		case "x":
			if(b == null) break;
			complete.add(String.valueOf((int) b.getLocation().getX()));
			break;
		case "y":
			if(b == null) break;
			complete.add(String.valueOf((int) b.getLocation().getY()));
			break;
		case "z":
			if(b == null) break;
			complete.add(String.valueOf((int) b.getLocation().getZ()));
			break;
		}
		return complete;
	}
}
