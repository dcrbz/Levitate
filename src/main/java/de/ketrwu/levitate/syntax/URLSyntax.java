package de.ketrwu.levitate.syntax;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.MessageBuilder;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;
import org.bukkit.command.CommandSender;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Checks if user-input is an url
 *
 * @author Kenneth Wussmann
 */
public class URLSyntax implements SyntaxHandler {

    @Override
    public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException {
        HashMap<String, String> replaces = new HashMap<String, String>();
        replaces.put("%arg%", passed);

        try {
            new URL(passed);
            if (parameter != null && !parameter.equals("")) {
                if (!passed.toLowerCase().startsWith(parameter.toLowerCase())) {
                    replaces.put("%parameter%", parameter);
                    throw new SyntaxResponseException(new MessageBuilder(Message.URLSYNTAX_DOES_NOT_START_WITH, TextMode.COLOR, replaces));
                }
            }
        } catch (MalformedURLException e) {
            throw new SyntaxResponseException(new MessageBuilder(Message.URLSYNTAX_URL_MALFORMED, TextMode.COLOR, replaces));
        }
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
        List<String> complete = new ArrayList<String>();
        complete.add("http://");
        complete.add("https://");
        complete.add("http://www.");
        complete.add("https://www.");
        complete.add("ftp://");
        return complete;
    }
}
