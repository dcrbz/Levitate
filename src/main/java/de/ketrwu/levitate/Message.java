package de.ketrwu.levitate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Holds default messages of Levitate.
 * Please read <a href="https://github.com/KennethWussmann/Levitate/wiki/5.-Modify-messages">this</a> to learn how to modify them the right way.
 * @author Kenneth Wussmann
 */
public enum Message {
	NO_PERMISSION("You don't have permission!"),
	ONLY_INGAME("This command is for ingame players!"),
	ONLY_CONSOLE("This command is only for the console!"),
	COMMAND_DOESNT_EXIST("This command doesn't exist!"),
	DEFAULTHELPMAP_HEADER("Help of %plugin%:"),
	DEFAULTHELPMAP_HELP_ELEMENT(" - %syntax% = %description%"),
	DEFAULTHELPMAP_NO_DESCRIPTION("No description"),
	DEFAULTHELPMAP_NO_COMMANDS("There are no commands like %command%!"),
	CI_ARG_HAS_TO_START_WITH_CHAR("The argument \"%arg%\" has to start with \"<\"!"),
	CI_ARG_HAS_TO_END_WITH_CHAR("The argument \"%arg%\" has to start with \">\"!"),
	CI_ARG_CANNOT_BE_UNLIMITED("The argument \"%arg%\" has to be last argument to be unlimited!"),
	CI_NO_SYNTAX("There is no syntax for \"%method%\"!"),
	CI_ERROR_AT_CHAR_IN_ARG("Error at character \"%char%\" in \"%arg%\"!"),
	CI_ERROR_AT_CHAR("Error at character \"%char%\"!"),
	CI_CMD_CANNOT_START_WITH("Command cannot start with \"<\"!"),
	CR_PARAMETERCOUNT_INVALID("ParameterCount of \"%method%\" has to be three!"),
	CR_PARAMETER_INVALID("Parameter %index% of \"%method%\" has to be %class%!"),
	BOOLEANSYNTAX_HAS_TO_BE_BOOLEAN("Argument \"%arg%\" has to be a boolean!"),
	CHOICESYNTAX_NOT_LIST("Parameter \"%arg%\" has to be a list of choices!"),
	CHOICESYNTAX_NOT_COMMA_SEPARATED("Parameter \"%arg%\" has to be a comma-separated list!"),
	CHOICESYNTAX_NOT_IN_LIST("Argument \"%arg%\" has to be a value of theese \"%list%\"!"),
	ENUMSYNTAX_NEEDS_CLASSPATH("Method \"enum\" needs the classpath to an enum!"),
	ENUMSYNTAX_CLASS_DOESNT_EXIST("Class \"%class%\" in method \"enum\" doesn\"t exist!"),
	ENUMSYNTAX_ARG_NOT_IN_ENUM("Argument \"%arg%\" has to be a value of theese \"%list%\"! It\"s case-insensitive."),
	EQUALSIGNORECASESYNTAX_DOESNT_EQUAL("Argument \"%arg%\" has to equal \"%value%\"!"),
	EQUALSSYNTAX_DOESNT_EQUAL("Argument \"%arg%\" has to equal \"%value%\"!"),
	INTEGERSYNTAX_HAS_NO_INTEGER("Argument \"%arg%\" has to be a number!"),
	INTEGERSYNTAX_HAS_TO_BE_POSITIVE_ZERO("Argument \"%arg%\" has to be a positive number or zero!"),
	INTEGERSYNTAX_HAS_TO_BE_NEGATIVE_ZERO("Argument \"%arg%\" has to be a negative number or zero!"),
	INTEGERSYNTAX_PARAMETER_MALFORMED("Parameter \"%parameter%\" is malformed!"),
	INTEGERSYNTAX_PARAMETER_MALFORMED_2("Parameter \"%parameter%\" is malformed! The left number has to be smaller then the right one."),
	INTEGERSYNTAX_HAS_TO_BE_INBETWEEN("Argument \"%arg%\" has to be inbetween \"%min%\" and \"%max%\"!"),
	DOUBLESYNTAX_HAS_NO_DOUBLE("Argument \"%arg%\" has to be a decimal number!"),
	DOUBLESYNTAX_HAS_TO_BE_POSITIVE_ZERO("Argument \"%arg%\" has to be a positive decimal number or zero!"),
	DOUBLESYNTAX_HAS_TO_BE_NEGATIVE_ZERO("Argument \"%arg%\" has to be a negative decimal number or zero!"),
	DOUBLESYNTAX_PARAMETER_MALFORMED("Parameter \"%parameter%\" is malformed!"),
	DOUBLESYNTAX_PARAMETER_MALFORMED_2("Parameter \"%parameter%\" is malformed! The left decimal number has to be smaller then the right one."),
	DOUBLESYNTAX_HAS_TO_BE_INBETWEEN("Argument \"%arg%\" has to be inbetween \"%min%\" and \"%max%\"!"),
	NOTEQUALSIGNORECASESYNTAX_CANNOT_EQUAL("Argument \"%arg%\" cannot equal \"%value%\"!"),
	NOTEQUALSSYNTAX_CANNOT_EQUAL("Argument \"%arg%\" cannot equal \"%value%\"!"),
	PLAYERSYNTAX_PLAYER_OFFLINE("The player \"%player%\" has to be online!"),
	PLAYERSYNTAX_PLAYER_ONLINE("The player \"%player%\" has to be offline!"),
	STRINGSYNTAX_CANNOT_BE_INT("Argmuent \"%arg%\" cannot be a number!"),
	STRINGSYNTAX_ONLY_LOWERCASE("Argument \"%arg%\" has to contain only lower-case letters!"),
	STRINGSYNTAX_ONLY_UPPERCASE("Argument \"%arg%\" has to contain only upper-case letters!"),
	ITEMSTACKSYNTAX_NO_INTEGER("Argument \"%arg%\" has to be an item separated by \":\"!"),
	ITEMSTACKSYNTAX_POSITIVE_INTEGER("Number \"%int%\" has to be positive or zero!"),
	ITEMSTACKSYNTAX_ITEM_NOT_FOUND("Item \"%arg%\" doesn't exist!"),
	WORLDSYNTAX_WORLD_DOES_NOT_EXIST("The world \"%world%\" doesn't exist!"),
	URLSYNTAX_URL_MALFORMED("The argument \"%arg%\" has to be an URL!"),
	URLSYNTAX_DOES_NOT_START_WITH("The url \"%arg%\" has to start with \"%parameter%\"!");
	
	private static YamlConfiguration config;
	private String message;
	
	/**
	 * Holds default messages of Levitate.
	 * Please read <a href="https://github.com/KennethWussmann/Levitate/wiki/5.-Modify-messages">this</a> to learn how to modify them the right way.
	 * @param message Message
	 */
	Message(String message) {
		this.message = message;
	}
	
	/**
	 * Get a Levitate-Message 
	 * @param mode RAW = Unmodified message <br />PLAIN = Strip all colors <br />COLOR = Get colored String and also translate alternate ColorCodes 
	 * @return
	 */
	public String get(TextMode mode) {
		String raw = message;
		if(config != null) {
			if(config.getString("levitate." + values()[ordinal()].toString()) != null) raw = config.getString("levitate." + values()[ordinal()].toString());
		}
		switch(mode) {
		case COLOR:
			return ChatColor.translateAlternateColorCodes('&', raw);
		case PLAIN:
			return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', raw));
		}
		return raw;
	}
	
	/**
	 * Get a Levitate-Message and replaces variables 
	 * @param mode RAW = Unmodified message <br />PLAIN = Strip all colors <br />COLOR = Get colored String and also translate alternate ColorCodes 
	 * @param replaces HashMap<String, String> with replaces to allow dynamic messages
	 * @return
	 */
	public String get(TextMode mode, HashMap<String, String> replaces) {
		String message = get(mode);
		for(String key : replaces.keySet()) 
			message = message.replace(key, replaces.get(key));
		return message;
	}
	
	/**
	 * Load a YAML-Configuration as Message-Source
	 * @param file Path to .yml file
	 */
	public static void loadConfig(File file) {
		config = new YamlConfiguration();
		try {
			config.load(file);
			for(Message message : values()) {
				config.addDefault("levitate." + message.toString(), message.get(TextMode.RAW));
			}
			config.options().copyDefaults(true);
			config.save(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TextMode for Levitate-Messages.<br /><br />
	 * 
	 * RAW = Unmodified message<br />
	 * PLAIN = Strip all colors<br />
	 * COLOR = Get colored String and also translate alternate ColorCodes 
	 * @author Kenneth Wussmann
	 */
	public enum TextMode {
		RAW, PLAIN, COLOR
	}
	
}
