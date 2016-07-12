package de.ketrwu.levitate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.annotation.Syntax;
import de.ketrwu.levitate.exception.CommandSyntaxException;
import de.ketrwu.levitate.exception.SyntaxAnnotationException;
import de.ketrwu.levitate.exception.SyntaxResponseException;
import de.ketrwu.levitate.handler.SyntaxHandler;
import de.ketrwu.levitate.syntax.BooleanSyntax;
import de.ketrwu.levitate.syntax.ChoiceIgnoreCaseSyntax;
import de.ketrwu.levitate.syntax.ChoiceSyntax;
import de.ketrwu.levitate.syntax.CoordinateSyntax;
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
	private static List<Object> syntaxClasses = new ArrayList<Object>();
	
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
		registerSyntax("coord", new CoordinateSyntax());
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
	 * Register all syntaxes in this class based on the @Syntax annotation.
	 * @param obj Class mit syntaxes
	 */
	public static void registerSyntaxes(final Object obj) {
		if(syntaxClasses.contains(obj)) return;
		syntaxClasses.add(obj);
		final HashMap<String, String> replaces = new HashMap<String, String>();
		try {
			for(final Method m : obj.getClass().getDeclaredMethods()) {
				m.setAccessible(true);
				replaces.put("%method%", obj.getClass().getName() + ": " + m.getName() + "()");
				if(m.isAnnotationPresent(Syntax.class)) {
					final Syntax annotation = m.getAnnotation(Syntax.class);
					if(annotation.parameter()) {
						if(annotation.parameterOptional()) {
							if(m.getParameterTypes().length > 3 || m.getParameterTypes().length < 2) {
								replaces.put("%amount%", "2 - 3");
								throw new SyntaxAnnotationException(new MessageBuilder(Message.SV_PARAMETERCOUNT_INVALID, TextMode.PLAIN, replaces));
							}
						} else {
							if(m.getParameterTypes().length != 3) {
								replaces.put("%amount%", "3");
								throw new SyntaxAnnotationException(new MessageBuilder(Message.SV_PARAMETERCOUNT_INVALID, TextMode.PLAIN, replaces));
							}
						}
					} else {
						if(m.getParameterTypes().length != 2) {
							replaces.put("%amount%", "2");
							throw new SyntaxAnnotationException(new MessageBuilder(Message.SV_PARAMETERCOUNT_INVALID, TextMode.PLAIN, replaces));
						}
					}
					if(m.getParameterTypes()[0] != CommandSender.class) {
						replaces.put("%index%", "0");
						replaces.put("%class%", "CommandSender");
						throw new SyntaxAnnotationException(new MessageBuilder(Message.SV_PARAMETER_INVALID, TextMode.PLAIN, replaces));
					}
					if(m.getParameterTypes()[1] != String.class) {
						replaces.put("%index%", "1");
						replaces.put("%class%", "String");
						throw new SyntaxAnnotationException(new MessageBuilder(Message.SV_PARAMETER_INVALID, TextMode.PLAIN, replaces));
					}
					if(m.getParameterTypes().length == 3) {
						if(m.getParameterTypes()[2] != String.class) {
							replaces.put("%index%", "2");
							replaces.put("%class%", "String");
							throw new SyntaxAnnotationException(new MessageBuilder(Message.SV_PARAMETER_INVALID, TextMode.PLAIN, replaces));
						}
					}


					replaces.put("%syntax%", annotation.syntax());
					
					registerSyntax(annotation.syntax(), new SyntaxHandler() {
						
						@Override
						public List<String> getTabComplete(CommandSender sender, String parameter, String passed) {
							if(m.getReturnType() == null) return null;
							try {
								if(annotation.parameter()) {
									return (List<String>) m.invoke(obj, sender, passed, parameter);
								} else {
									return (List<String>) m.invoke(obj, sender, passed);
								}
							} catch (IllegalAccessException | IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								
							}
							return null;
						}
						
						@Override
						public void check(CommandSender sender, String parameter, String passed) throws CommandSyntaxException, SyntaxResponseException {
							if(annotation.parameter() == false && parameter != null) throw new CommandSyntaxException(new MessageBuilder(Message.SV_PARAMETER_DISALLOWED, TextMode.COLOR, replaces));
							switch(annotation.commandType()) {
							case CONSOLE:
								if(sender instanceof Player) {
									replaces.put("%syntax%", annotation.commandType().toString());
									throw new CommandSyntaxException(new MessageBuilder(Message.SV_DISALLOWED_FOR_EXECUTOR, TextMode.COLOR, replaces));
								}
								break;
							case PLAYER:
								if(!(sender instanceof Player)) {
									replaces.put("%syntax%", annotation.commandType().toString());
									throw new CommandSyntaxException(new MessageBuilder(Message.SV_DISALLOWED_FOR_EXECUTOR, TextMode.COLOR, replaces));
								}
								break;
							}
							if(annotation.parameter()) {
								if(annotation.parameterOptional()) {
									try {
										m.invoke(obj, sender, passed, parameter);
									} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
										e.printStackTrace();
									}
								} else {
									if(parameter == null) {
										throw new CommandSyntaxException(new MessageBuilder(Message.SV_PARAMETER_NEEDED, TextMode.COLOR, replaces));
									} else {
										try {
											m.invoke(obj, sender, passed, parameter);
										} catch (IllegalAccessException | IllegalArgumentException e) {
											e.printStackTrace();
										} catch (InvocationTargetException e) {
											if(e.getCause() != null) {
												if(e.getCause() instanceof SyntaxResponseException) {
													SyntaxResponseException ex = (SyntaxResponseException) e.getCause();
													throw new SyntaxResponseException(ex.getMessageBuilder());
												} else if(e.getCause() instanceof CommandSyntaxException) {
													CommandSyntaxException ex = (CommandSyntaxException) e.getCause();
													throw new CommandSyntaxException(ex.getMessageBuilder());
												}
											}
										}
									}
								}
							} else {
								try {
									m.invoke(obj, sender, passed);
								} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
						
					});
					
				}
			}
		} catch (SyntaxAnnotationException e) {
			e.printStackTrace();
		}		
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
	
	public static boolean isStringList(Class clazz) {
		final Type type = clazz.getGenericSuperclass();
	    if (type instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType)type;
	        try {
				Class c = Class.forName(pType.getActualTypeArguments()[0].getTypeName());
				for (int i = 0; i < 5; i++) {
					if(c.isInstance(new ArrayList<String>())) return true;
					c = c.getSuperclass();
				}
				
			} catch (ClassNotFoundException e) {
			}
	    }
	    return false;
	}
	
//	public static Class getGeneric(Field f) {
//		Type type = f.getGenericType();
//	    if (type instanceof ParameterizedType) {
//	        ParameterizedType pType = (ParameterizedType)type;
//	        try {
//				return Class.forName(pType.getActualTypeArguments()[0].getTypeName());
//			} catch (ClassNotFoundException e) {
//			}
//	    }
//	    return null;
//	}
	
}
