package de.ketrwu.levitate.syntax;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.MessageBuilder;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

/**
 * Checks if user-input is not string. Case-insensitive.
 *
 * @author Kenneth Wussmann
 */
public class NotEqualsIgnoreCaseSyntax implements SyntaxHandler {

    @Override
    public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException {
        HashMap<String, String> replaces = new HashMap<String, String>();
        replaces.put("%arg%", passed);
        replaces.put("%value%", parameter);
        if (parameter.equalsIgnoreCase(passed))
            throw new SyntaxResponseException(new MessageBuilder(Message.NOTEQUALSIGNORECASESYNTAX_CANNOT_EQUAL, TextMode.COLOR, replaces));
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
        return null;
    }

}
