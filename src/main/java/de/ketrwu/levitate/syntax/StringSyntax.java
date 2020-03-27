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
 * Checks if user-input is a string
 *
 * @author Kenneth Wussmann
 */
public class StringSyntax implements SyntaxHandler {

    @Override
    public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException {
        HashMap<String, String> replaces = new HashMap<String, String>();
        replaces.put("%arg%", passed);
        if (parameter.equals("") || parameter.equals("aA")) {
            return;
        }
        if (parameter.equals("a")) {
            if (!isLowerCase(passed))
                throw new SyntaxResponseException(new MessageBuilder(Message.STRINGSYNTAX_ONLY_LOWERCASE, TextMode.COLOR, replaces));
        }
        if (parameter.equals("A")) {
            if (!isUpperCase(passed))
                throw new SyntaxResponseException(new MessageBuilder(Message.STRINGSYNTAX_ONLY_UPPERCASE, TextMode.COLOR, replaces));
        }
        return;
    }

    public boolean isLowerCase(String input) {
        for (char ch : input.toCharArray()) {
            String c = String.valueOf(ch);
            if (!c.equals(c.toLowerCase())) return false;
        }
        return true;
    }

    public boolean isUpperCase(String input) {
        for (char ch : input.toCharArray()) {
            String c = String.valueOf(ch);
            if (!c.equals(c.toUpperCase())) return false;
        }
        return true;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
        return null;
    }

}
