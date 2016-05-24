package de.ketrwu.levitate.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.SyntaxHandler;
import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Checks if user-input is a playername
 * @author Kenneth Wussmann
 */
public class PlayerSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException {
		if(parameter.equalsIgnoreCase("online") == false && parameter.equalsIgnoreCase("offline") == false && parameter.equals("") == false) throw new CommandSyntaxException("Method 'player' doesn't supports parameter '"+parameter+"'!");
		OfflinePlayer p = Bukkit.getOfflinePlayer(passed);
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%player%", p.getName());
		if(p == null) p = Bukkit.getOfflinePlayer(UUID.fromString(passed));
		if(p == null) throw new Synt
		if(parameter.equalsIgnoreCase("online")) {
			if(!p.isOnline()) throw new SyntaxResponseException(Message.PLAYERSYNTAX_PLAYER_OFFLINE.get(TextMode.COLOR, replaces));
		} else if(parameter.equalsIgnoreCase("offline")) {
			if(p.isOnline()) throw new SyntaxResponseException(Message.PLAYERSYNTAX_PLAYER_ONLINE.get(TextMode.COLOR, replaces));
		}
	}
	
	@Override
	public List<String> getTabComplete(String parameter, String passed) {
		List<String> playerList = new ArrayList<String>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			playerList.add(p.getName());
		}
		return playerList;
	}

}
