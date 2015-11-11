package de.ketrwu.levitate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.plugin.java.JavaPlugin;

import de.ketrwu.levitate.syntax.BooleanSyntax;
import de.ketrwu.levitate.syntax.ChoiceIgnoreCaseSyntax;
import de.ketrwu.levitate.syntax.ChoiceSyntax;
import de.ketrwu.levitate.syntax.DoubleSyntax;
import de.ketrwu.levitate.syntax.EnumSyntax;
import de.ketrwu.levitate.syntax.EqualsIgnoreCaseSyntax;
import de.ketrwu.levitate.syntax.EqualsSyntax;
import de.ketrwu.levitate.syntax.IntegerSyntax;
import de.ketrwu.levitate.syntax.ItemStackSyntax;
import de.ketrwu.levitate.syntax.NotEqualsIgnoreCaseSyntax;
import de.ketrwu.levitate.syntax.NotEqualsSyntax;
import de.ketrwu.levitate.syntax.PlayerSyntax;
import de.ketrwu.levitate.syntax.StringSyntax;
import de.ketrwu.levitate.syntax.URLSyntax;
import de.ketrwu.levitate.syntax.WildcardSyntax;
import de.ketrwu.levitate.syntax.WorldSyntax;

/**
 * Handles SyntaxHandler's
 * @author Kenneth Wussmann
 */
public class SyntaxValidations {
	
	private static HashMap<String, SyntaxHandler> syntaxes = new HashMap<String, SyntaxHandler>();
	
	/**
	 * Register default syntaxes to create your command
	 * @param plugin Instance of JavaPlugin
	 */
	public static void registerDefaultSyntax(JavaPlugin plugin) {
		registerSyntax("boolean", new BooleanSyntax());
		registerSyntax("int", new IntegerSyntax());
		registerSyntax("double", new DoubleSyntax());
		registerSyntax("string", new StringSyntax());
		registerSyntax("eq", new EqualsSyntax());
		registerSyntax("neq", new NotEqualsSyntax());
		registerSyntax("eqi", new EqualsIgnoreCaseSyntax());
		registerSyntax("neqi", new NotEqualsIgnoreCaseSyntax());
		registerSyntax("enum", new EnumSyntax());
		registerSyntax("choice", new ChoiceSyntax());
		registerSyntax("choicei", new ChoiceIgnoreCaseSyntax());
		registerSyntax("player", new PlayerSyntax());
		registerSyntax("item", new ItemStackSyntax(plugin));
		registerSyntax("*", new WildcardSyntax());
		registerSyntax("world", new WorldSyntax());
		registerSyntax("url", new URLSyntax());
	}
	
	/**
	 * Register your own syntax.
	 * @param method The base command of your syntax
	 * @param handler The handler to check values against your syntax
	 */
	public static void registerSyntax(String method, SyntaxHandler handler) {
		syntaxes.put(method, handler);
	}
	
	/**
	 * Get matches of RegEx
	 * @param p RegEx
	 * @param input 
	 * @return
	 */
	public static Iterable<MatchResult> allMatches(final Pattern p, final CharSequence input) {
		return new Iterable<MatchResult>() {
			public Iterator<MatchResult> iterator() {
				return new Iterator<MatchResult>() {
					final Matcher matcher = p.matcher(input);
					MatchResult pending;

					public boolean hasNext() {
						if (pending == null && matcher.find()) {
							pending = matcher.toMatchResult();
						}
						return pending != null;
					}

					public MatchResult next() {
						if (!hasNext()) {
							throw new NoSuchElementException();
						}
						MatchResult next = pending;
						pending = null;
						return next;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	/**
	 * Check if SyntaxHandler exists for <i>method</i>
	 * @param method Argument in Levitate-Syntax
	 * @return
	 */
	public static boolean existHandler(String method) {
		for(String m : syntaxes.keySet()) 
			if(m.equalsIgnoreCase(method)) return true;
		return false;
	}
	
	/**
	 * Get syntaxes 
	 * @return
	 */
	public static HashMap<String, SyntaxHandler> getSyntaxes() {
		return syntaxes;
	}

	/**
	 * Remove all syntaxes
	 */
	public static void clearSyntaxes() {
		syntaxes.clear();
	}
	
}
