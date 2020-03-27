package de.ketrwu.levitate.syntax;

import de.ketrwu.levitate.CommandRegistry;
import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.MessageBuilder;
import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Checks if user-input is a value of entered enum. Case-insensitive
 *
 * @author Kenneth Wussmann
 */
public class EnumSyntax implements SyntaxHandler {

    @Override
    public void check(CommandSender sender, String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException {
        if (parameter.equals(""))
            throw new CommandSyntaxException(new MessageBuilder(Message.ENUMSYNTAX_NEEDS_CLASSPATH, TextMode.COLOR));
        HashMap<String, String> replaces = new HashMap<String, String>();
        replaces.put("%class%", parameter);
        if (!CommandRegistry.existClass(parameter))
            throw new CommandSyntaxException(new MessageBuilder(Message.ENUMSYNTAX_CLASS_DOESNT_EXIST, TextMode.COLOR, replaces));
        try {
            Class<?> cls = Class.forName(parameter);
            cls.getDeclaredField(passed.toUpperCase());
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            if (e instanceof NoSuchFieldException) {
                try {
                    Class<?> cls = Class.forName(parameter);
                    String fields = "";
                    for (Field f : cls.getFields()) {
                        fields += correctCase(f.getName()) + ", ";
                    }
                    fields = fields.substring(0, fields.length() - 2);
                    replaces.clear();
                    replaces.put("%arg%", correctCase(passed));
                    replaces.put("%list%", fields);
                    throw new SyntaxResponseException(new MessageBuilder(Message.ENUMSYNTAX_ARG_NOT_IN_ENUM, TextMode.COLOR, replaces));
                } catch (ClassNotFoundException e2) {
                }
            }
            e.printStackTrace();
        }
    }

    public String correctCase(String input) {
        String i = "";
        int count = 0;
        for (char ch : input.toCharArray()) {
            String c = String.valueOf(ch).toLowerCase();
            if (count == 0) c = c.toUpperCase();
            i += c;
            count++;
        }
        return i;
    }

    @Override
    public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
        List<String> complete = new ArrayList<String>();
        try {
            Class<?> cls = Class.forName(parameter);
            for (Field f : cls.getFields()) {
                complete.add(correctCase(f.getName()));
            }
        } catch (ClassNotFoundException e2) {
        }
        return complete;
    }

}
