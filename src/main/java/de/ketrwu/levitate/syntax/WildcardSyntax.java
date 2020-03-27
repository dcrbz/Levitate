package de.ketrwu.levitate.syntax;

import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Checks if user-input is anything
 *
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
