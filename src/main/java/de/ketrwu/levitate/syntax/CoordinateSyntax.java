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
import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;

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
			throw new SyntaxResponseException(Message.COORDSYNTAX_HAS_TO_BE_WORLD.get(TextMode.COLOR, replaces));
		case "blockx":
		case "blocky":
		case "blockz":
		case "playerx":
		case "playery":
		case "playerz":
		case "yaw":
		case "pitch":
			if(!isInt(passed) && !isDouble(passed) && !isFloat(passed)) throw new SyntaxResponseException(Message.COORDSYNTAX_HAS_TO_BE_NUMBER.get(TextMode.COLOR, replaces));
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
	
	public boolean isDouble(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (Exception e) { }
		return false;
	}
	
	public boolean isFloat(String val) {
		try {
			Float.parseFloat(val);
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
		case "blockx":
			if(b == null) break;
			complete.add(String.valueOf(b.getLocation().getX()));
			break;
		case "blocky":
			if(b == null) break;
			complete.add(String.valueOf(b.getLocation().getY()));
			break;
		case "blockz":
			if(b == null) break;
			complete.add(String.valueOf(b.getLocation().getZ()));
		case "playerx":
			if(b == null) break;
			complete.add(String.valueOf(p.getLocation().getX()));
			break;
		case "playery":
			if(b == null) break;
			complete.add(String.valueOf(p.getLocation().getY()));
			break;
		case "playerz":
			if(b == null) break;
			complete.add(String.valueOf(p.getLocation().getZ()));
		case "yaw":
			if(b == null) break;
			complete.add(String.valueOf(p.getLocation().getYaw()));
		case "pitch":
			if(b == null) break;
			complete.add(String.valueOf(p.getLocation().getPitch()));
			break;
		}
		return complete;
	}
}
