package de.ketrwu.levitate.syntax;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.MessageBuilder;
import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks if user-input is in choice list. Case-insensitive
 *
 * @author Kenneth Wussmann
 */
public class ChoiceIgnoreCaseSyntax implements SyntaxHandler {

    @Override
    public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException {
        HashMap<String, String> replaces = new HashMap<String, String>();
        replaces.put("%arg%", parameter);
        if (parameter.equals(""))
            throw new CommandSyntaxException(new MessageBuilder(Message.CHOICESYNTAX_NOT_LIST, TextMode.COLOR, replaces));
        Pattern p = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        Matcher m = p.matcher(parameter);
        if (!m.find())
            throw new CommandSyntaxException(new MessageBuilder(Message.CHOICESYNTAX_NOT_COMMA_SEPARATED, TextMode.COLOR, replaces));
        String[] ch = parameter.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        List<String> choices = new ArrayList<String>();
        String strList = "";
        for (String s : ch) {
            strList += s + ", ";
            choices.add(s.toLowerCase());
        }
        strList = strList.substring(0, strList.length() - 2);
        replaces.put("%arg%", passed);
        replaces.put("%list%", strList);
        if (!choices.contains(passed.toLowerCase()))
            throw new SyntaxResponseException(new MessageBuilder(Message.CHOICESYNTAX_NOT_IN_LIST, TextMode.COLOR, replaces));

    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
        Pattern p = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        Matcher m = p.matcher(parameter);
        String[] ch = parameter.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        List<String> choices = new ArrayList<String>();
        for (String s : ch) {
            choices.add(s.toLowerCase());
        }
        return choices;
    }

}
