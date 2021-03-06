package de.ketrwu.levitate;

import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.bukkit.LevitateCommandPreprocessEvent;
import de.ketrwu.levitate.bukkit.LevitateMessagePreprocessEvent;
import de.ketrwu.levitate.exception.*;
import de.ketrwu.levitate.handler.CommandHandler;
import de.ketrwu.levitate.handler.MessageHandler;
import de.ketrwu.levitate.handler.PermissionHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Handles Levitate-Commands
 *
 * @author Kenneth Wussmann
 */
public class CommandRegistry {

    private HashMap<CommandInformation, CommandHandler> commands = new HashMap<CommandInformation, CommandHandler>();
    private List<String> aliases = new ArrayList<String>();
    private List<Object> commandClasses = new ArrayList<Object>();
    private PermissionHandler permissionHandler = null;
    private HelpMap helpMap = null;
    private MessageHandler messageHandler = null;
    private Plugin plugin = null;

    /**
     * This class registers your commands and handels them.
     * This constructor is for Bukkit/Spigot plugins
     *
     * @param plugin The JavaPlugin instance
     */
    public CommandRegistry(Plugin plugin) {
        if (plugin == null) return;
        this.plugin = plugin;
        registerDefaultMessageHandler();
        registerBukkitPermissionHandler();
        registerDefaultHelpMap();
    }

    /**
     * Register all commands with annotations in given class
     *
     * @param obj Class with commands
     */
    public void registerCommands(final Object obj) {
        if (commandClasses.contains(obj)) return;
        commandClasses.add(obj);
        HashMap<String, String> replaces = new HashMap<String, String>();
        try {
            for (final Method m : obj.getClass().getDeclaredMethods()) {
                replaces.put("%method%", obj.getClass().getName() + ": " + m.getName() + "()");
                CommandInformation cmd = null;
                String[] aliases = null;
                if (m.isAnnotationPresent(de.ketrwu.levitate.annotation.Command.class)) {
                    if (m.getParameterTypes().length != 3)
                        throw new CommandAnnotationException(new MessageBuilder(Message.CR_PARAMETERCOUNT_INVALID, TextMode.PLAIN, replaces));
                    if (m.getParameterTypes()[0] != CommandSender.class) {
                        replaces.put("%index%", "0");
                        replaces.put("%class%", "CommandSender");
                        throw new CommandAnnotationException(new MessageBuilder(Message.CR_PARAMETER_INVALID, TextMode.PLAIN, replaces));
                    }
                    if (m.getParameterTypes()[1] != String.class) {
                        replaces.put("%index%", "1");
                        replaces.put("%class%", "String");
                        throw new CommandAnnotationException(new MessageBuilder(Message.CR_PARAMETER_INVALID, TextMode.PLAIN, replaces));
                    }
                    if (m.getParameterTypes()[2] != ParameterSet.class) {
                        replaces.put("%index%", "2");
                        replaces.put("%class%", "ParameterSet");
                        throw new CommandAnnotationException(new MessageBuilder(Message.CR_PARAMETER_INVALID, TextMode.PLAIN, replaces));
                    }

                    de.ketrwu.levitate.annotation.Command commandAnnotation = m.getAnnotation(de.ketrwu.levitate.annotation.Command.class);
                    cmd = new CommandInformation(commandAnnotation.syntax());

                    if (!commandAnnotation.readable().equals("")) cmd.setReadable(commandAnnotation.readable());
                    if (!commandAnnotation.permission().equals("")) cmd.setPermission(commandAnnotation.permission());
                    if (!commandAnnotation.description().equals(""))
                        cmd.setDescription(commandAnnotation.description());
                    if (commandAnnotation.aliases().length > 0) aliases = commandAnnotation.aliases();

                    if (aliases == null) {
                        register(cmd, new CommandHandler() {

                            @Override
                            public void execute(CommandSender sender, String command, ParameterSet args) {
                                try {
                                    m.invoke(obj, sender, command, args);
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                    } else {
                        register(cmd, aliases, new CommandHandler() {

                            @Override
                            public void execute(CommandSender sender, String command, ParameterSet args) {
                                try {
                                    m.invoke(obj, sender, command, args);
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                    }
                }
            }
        } catch (CommandAnnotationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register new command
     *
     * @param info    CommandInformation with syntax, permission etc
     * @param handler The CommandHandler which handels the execution of the command
     */
    public void register(CommandInformation info, CommandHandler handler) {
        registerBukkitCommand(info, null);
        commands.put(info, handler);
    }

    /**
     * Register alias, only used internal
     *
     * @param alias Alias as string
     */
    private void registerAlias(String alias) {
        if (aliases.contains(alias.toLowerCase())) return;
        aliases.add(alias.toLowerCase());
    }

    /**
     * Register new command with aliases
     *
     * @param info    CommandInformation with syntax, permission etc
     * @param aliases Array with aliases
     * @param handler The CommandHandler which handels the execution of the command
     */
    public void register(CommandInformation info, String[] aliases, CommandHandler handler) {

        for (String alias : aliases) {
            String ns = "";
            String syntax = info.getSyntax();
            if (syntax.startsWith("?") || syntax.startsWith("/") || syntax.startsWith("$")) {
                ns = syntax.substring(0, 1);
                syntax = syntax.substring(1);
            }
            ns += alias + " ";
            if (syntax.contains(" ")) {
                String[] split = syntax.split(" ");
                for (int i = 0; i < split.length; i++) {
                    if (i != 0) {
                        ns += split[i] + " ";
                    }
                }
            }
            if (ns.endsWith(" ")) ns = ns.substring(0, ns.length() - 1);
            CommandInformation cinfo = new CommandInformation(ns, info.getPermission());
            cinfo.setPermission(info.getPermission());
            cinfo.setDescription(info.getDescription());
            if (info.getReadable() != null) cinfo.setReadable(info.getReadable());
            registerAlias(alias);
            commands.put(cinfo, handler);
        }
        registerBukkitCommand(info, aliases);
        commands.put(info, handler);
    }

    /**
     * Registers CommandInformation to Bukkit, only used internal
     *
     * @param info    CommandInformation
     * @param aliases Array with aliases
     */
    private void registerBukkitCommand(final CommandInformation info, String[] aliases) {
        if (aliases == null) aliases = new String[]{};
        try {
            final Field f = getPlugin().getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap cmap = (CommandMap) f.get(getPlugin().getServer());
            cmap.register(info.getCommand(), new Command(info.getCommand(), info.getDescription(), info.getSyntax(), new ArrayList<String>(Arrays.asList(aliases))) {

                @Override
                public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
                    List<String> complete = handleTabComplete(sender, alias, args);
                    Set<String> setItems = new LinkedHashSet<String>(complete);
                    complete.clear();
                    complete.addAll(setItems);
                    if (complete == null) return super.tabComplete(sender, alias, args);
                    return complete;
                }

                @Override
                public boolean execute(CommandSender sender, String arg1, String[] arg2) {
                    try {
                        return playerPassCommand(sender, arg1, arg2);
                    } catch (CommandSyntaxException | NoPermissionException | SyntaxResponseException | ExecutorIncompatibleException e) {
                        if (e instanceof NoPermissionException) {
                            LevitateMessagePreprocessEvent preprocessEvent = new LevitateMessagePreprocessEvent(getPlugin(), sender, new MessageBuilder(Message.NO_PERMISSION, TextMode.COLOR));
                            Bukkit.getPluginManager().callEvent(preprocessEvent);
                            if (!preprocessEvent.isCancelled()) {
                                if (preprocessEvent.getMessage() != null)
                                    getMessageHandler().sendMessage(sender, preprocessEvent.getMessageBuilder());
                            }
                            return true;
                        }
                        if (e instanceof SyntaxResponseException) {
                            SyntaxResponseException ex = (SyntaxResponseException) e;
                            LevitateMessagePreprocessEvent preprocessEvent = new LevitateMessagePreprocessEvent(getPlugin(), sender, ex.getMessageBuilder());
                            Bukkit.getPluginManager().callEvent(preprocessEvent);
                            if (!preprocessEvent.isCancelled()) {
                                if (preprocessEvent.getMessage() != null)
                                    getMessageHandler().sendMessage(sender, preprocessEvent.getMessageBuilder());
                            }
                            return true;
                        }

                        if (e instanceof ExecutorIncompatibleException) {
                            ExecutorIncompatibleException ex = (ExecutorIncompatibleException) e;
                            LevitateMessagePreprocessEvent preprocessEvent = new LevitateMessagePreprocessEvent(getPlugin(), sender, ex.getMessageBuilder());
                            Bukkit.getPluginManager().callEvent(preprocessEvent);
                            if (!preprocessEvent.isCancelled()) {
                                if (preprocessEvent.getMessage() != null)
                                    getMessageHandler().sendMessage(sender, preprocessEvent.getMessageBuilder());
                            }
                            return true;
                        }


                        e.printStackTrace();
                    }
                    return false;
                }
            });
        } catch (IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Get strings which fit the entered argument
     *
     * @param sender  CommandSender
     * @param command Base-Command or alias
     * @param args
     * @return List of strings which could fit the entered argument
     */
    private List<String> handleTabComplete(CommandSender sender, String command, String[] args) {
        if (args.length == 0) return null;
        List<String> complete = new ArrayList<String>();
        int lastArg = args.length - 1;
        String arg = args[lastArg];

        for (CommandInformation info : commands.keySet()) {
            if (!info.getCommand().equalsIgnoreCase(command)) continue;
            if (permissionHandler != null && info.getPermission() != null) {
                if (!permissionHandler.hasPermission(sender, info.getPermission())) {
                    continue;
                }
            }
            CommandExecutor cmdExec = null;
            if (sender instanceof Player) {
                cmdExec = CommandExecutor.PLAYER;
            } else {
                cmdExec = CommandExecutor.CONSOLE;
            }

            switch (info.getCommandExecutor()) {
                case CONSOLE:
                    if (cmdExec != CommandExecutor.CONSOLE) continue;
                    break;
                case PLAYER:
                    if (cmdExec != CommandExecutor.PLAYER) continue;
                    break;
            }
            Argument exArg = null;
            try {
                exArg = info.getArgs().get(lastArg);
            } catch (IndexOutOfBoundsException e) {

            }
            if (exArg == null) continue;
            List<String> l = exArg.getHandler().getTabComplete(sender, exArg.getParameter(), arg);
            if (l != null && l.size() > 0) complete.addAll(l);
        }
        Iterator<String> iComplete = complete.iterator();
        while (iComplete.hasNext()) {
            String str = iComplete.next();
            if (!str.toLowerCase().startsWith(arg.toLowerCase())) iComplete.remove();
        }
        Collections.sort(complete, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        return complete;
    }

    /**
     * Register default Bukkit/Spigot PermissionHandler.
     * It uses the hasPermission() method of Bukkit/Spigot to check permissions.
     */
    public void registerBukkitPermissionHandler() {
        permissionHandler = new PermissionHandler() {

            @Override
            public boolean hasPermission(CommandSender sender, String permission) {
                if (sender instanceof CommandSender) {
                    if (!sender.hasPermission(permission)) return false;
                }
                return true;
            }
        };
    }

    /**
     * Register your own PermissionHandler
     *
     * @param permissionHandler PermissionHandler wich checks whether the sender has permission to execute the command
     */
    public void registerPermissionHandler(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    /**
     * Register default HelpMap
     */
    public void registerDefaultHelpMap() {
        this.helpMap = new DefaultHelpMap(this);
    }

    /**
     * Register default MessageHandler
     */
    public void registerDefaultMessageHandler() {
        this.messageHandler = new DefaultMessageHandler();
    }

    /**
     * Register own HelpMap
     *
     * @param helpMap Handles the help-message
     */
    public void registerHelpMap(HelpMap helpMap) {
        this.helpMap = helpMap;
    }

    /**
     * Register own MessageHandler
     *
     * @param messageHandler
     */
    public void registerMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Executes a command as a Bukkit/Spigot player or console.
     * You don't need to call it.
     *
     * @param sender  The sender of the command
     * @param command The base-command
     * @param args    The arguments
     * @return
     * @throws CommandSyntaxException
     * @throws SyntaxResponseException
     * @throws NoPermissionException
     * @throws ExecutorIncompatibleException
     */
    public boolean playerPassCommand(CommandSender sender, String command, String[] args) throws CommandSyntaxException, NoPermissionException, SyntaxResponseException, ExecutorIncompatibleException {
        CommandExecutor ce = CommandExecutor.PLAYER;
        if (!(sender instanceof Player)) ce = CommandExecutor.CONSOLE;
        boolean found = false;
        SyntaxResponseException exeption = null;
        ExecutorIncompatibleException execIncompatible = null;

        for (CommandInformation i : commands.keySet()) {
            if (found == true) continue;
            try {
                if (i.matches(sender, ce, command, args)) {
                    if (permissionHandler != null && i.getPermission() != null) {
                        if (!permissionHandler.hasPermission(sender, i.getPermission())) {
                            throw new NoPermissionException(new MessageBuilder(Message.NO_PERMISSION, TextMode.COLOR));
                        }
                    }
                    ParameterSet ps = new ParameterSet(args);
                    LevitateCommandPreprocessEvent preprocessEvent = new LevitateCommandPreprocessEvent(getPlugin(), sender, i, ps);
                    Bukkit.getPluginManager().callEvent(preprocessEvent);
                    if (!preprocessEvent.isCancelled())
                        commands.get(i).execute(sender, command, new ParameterSet(args));
                    found = true;
                }
            } catch (ExecutorIncompatibleException | SyntaxResponseException e) {
                if (e instanceof ExecutorIncompatibleException) {
                    execIncompatible = (ExecutorIncompatibleException) e;
                } else {
                    exeption = (SyntaxResponseException) e;
                }
            }
        }
        if (found == false && execIncompatible != null) {
            throw execIncompatible;
        } else if (exeption != null && found == false) {
            throw exeption;
        } else if (helpMap != null && found == false) {
            helpMap.onHelp(sender, command, args);
        }
        return found;
    }

    /**
     * Checks if given class exists
     *
     * @param clazz
     * @return
     */
    public static boolean existClass(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Get all commands registered
     *
     * @return
     */
    public HashMap<CommandInformation, CommandHandler> getCommands() {
        return commands;
    }

    /**
     * Get the current PermissionHandler
     *
     * @return
     */
    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    /**
     * Get the plugin instance of the plugin registered this CommandRegistry
     *
     * @return
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Get the current HelpMap
     *
     * @return
     */
    public HelpMap getHelpMap() {
        return helpMap;
    }

    /**
     * Get the current MessageHandler
     *
     * @return
     */
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }


}
