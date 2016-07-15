package de.ketrwu.levitate.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.MessageBuilder;
import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;

/**
 * Checks if user-input is a playername
 * @author Kenneth Wussmann
 */
public class PlayerSyntax implements SyntaxHandler {

	@Override
	public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException {
		if(parameter.equalsIgnoreCase("online") == false && parameter.equalsIgnoreCase("offline") == false && parameter.equals("") == false) throw new CommandSyntaxException(new MessageBuilder(Message.PLAYERSYNTAX_PARAMETER_MALFORMED, TextMode.COLOR).replace("%parameter%", parameter));
		OfflinePlayer p = null;
		HashMap<String, String> replaces = new HashMap<String, String>();
		if(passed.length() > 16 && passed.contains("-")) {
			replaces.put("%uuid%", passed);
			replaces.put("%player%", passed);
			try {
				p = Bukkit.getOfflinePlayer(UUID.fromString(passed));
				if(p == null) throw new SyntaxResponseException(new MessageBuilder(Message.PLAYERSYNTAX_PLAYER_NOT_FOUND, TextMode.COLOR, replaces));
				if(p.getName() == null || p.getName().equalsIgnoreCase(passed)) throw new SyntaxResponseException(new MessageBuilder(Message.PLAYERSYNTAX_PLAYER_NOT_FOUND, TextMode.COLOR, replaces));
			} catch (IllegalArgumentException e) {
				throw new SyntaxResponseException(new MessageBuilder(Message.PLAYERSYNTAX_UUID_MALFORMED, TextMode.COLOR, replaces));
			}
		} else {
			p = Bukkit.getOfflinePlayer(passed);
		}
		if(p == null) throw new SyntaxResponseException(new MessageBuilder(Message.PLAYERSYNTAX_PLAYER_NOT_FOUND, TextMode.COLOR, replaces));
		replaces.put("%player%", p.getName());
		if(parameter.equalsIgnoreCase("online")) {
			if(!p.isOnline()) throw new SyntaxResponseException(new MessageBuilder(Message.PLAYERSYNTAX_PLAYER_OFFLINE, TextMode.COLOR, replaces));
		} else if(parameter.equalsIgnoreCase("offline")) {
			if(p.isOnline()) throw new SyntaxResponseException(new MessageBuilder(Message.PLAYERSYNTAX_PLAYER_ONLINE, TextMode.COLOR, replaces));
		}
	}
	
	@Override
	public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
		List<String> playerList = new ArrayList<String>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			playerList.add(p.getName());
		}
		return playerList;
	}

}
