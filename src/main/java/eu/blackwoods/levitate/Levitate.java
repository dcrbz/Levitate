package eu.blackwoods.levitate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bundles everything you need to use Levitate.
 * Read <a href="https://github.com/KennethWussmann/Levitate/wiki/2.-First-command">this page</a> to create your first Levitate-Command.
 * @author Kenneth Wussmann
 */

public class Levitate {
	
	private JavaPlugin plugin;
	private CommandRegistry registry;

	/**
	 * Bundles everything you need to use Levitate.
	 * Read <a href="https://github.com/KennethWussmann/Levitate/wiki/2.-First-command">this page</a> to create your first Levitate-Command.
	 * @param plugin Your plugin instance
	 */
	public Levitate(JavaPlugin plugin) {
		this.plugin = plugin;
		SyntaxValidations.registerDefaultSyntax(plugin);
		registry = new CommandRegistry(plugin);
		registry.registerBukkitPermissionHandler();
		registry.registerDefaultHelpMap();
	}

	/**
	 * Bundles everything you need to use Levitate. 
	 * Pass <b>true</b> to create a <i>messages.yml</i>.
	 * Read more about the <i>messages.yml</i> <a href="https://github.com/KennethWussmann/Levitate/wiki/5.-Modify-messages">here</a>.
	 * Read <a href="https://github.com/KennethWussmann/Levitate/wiki/2.-First-command">this page</a> to create your first Levitate-Command.
	 * @param plugin
	 * @param createYAML
	 */
	public Levitate(JavaPlugin plugin, boolean createYAML) {
		this.plugin = plugin;
		SyntaxValidations.registerDefaultSyntax(plugin);
		registry = new CommandRegistry(plugin);
		registry.registerBukkitPermissionHandler();
		registry.registerDefaultHelpMap();
		if(createYAML) {
			File pluginFolder = plugin.getDataFolder();
			File config = new File(pluginFolder, "messages.yml");
			if(!pluginFolder.exists()) pluginFolder.mkdirs();
			if(!config.exists()) {
				InputStream jarURL = Levitate.class.getResourceAsStream("/messages.yml");
				try {
					if(jarURL == null) {
						config.createNewFile();
					} else {
						copyFile(jarURL, config);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			Message.loadConfig(config);
		}
	}

	/**
	 * Get instance of JavaPlugin used to create Levitate-Instance
	 * @return
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}

	/**
	 * Get instance of CommandRegistry which holds your Levitate-Commands
	 * @return
	 */
	public CommandRegistry getCommandRegistry() {
		return registry;
	}
	
	/**
	 * Register all commands with annotations in given class
	 * @param obj Class with commands
	 */
	public void registerCommands(Object obj) {
		getCommandRegistry().registerCommands(obj);
	}

	/**
	 * Register new command with aliases
	 * @param info CommandInformation with syntax, permission etc
	 * @param aliases Array with aliases
	 * @param handler The CommandHandler which handels the execution of the command
	 */
	public void register(CommandInformation info, String[] aliases, CommandHandler handler) {
		getCommandRegistry().register(info, aliases, handler);
	}
	
	/**
	 * Register new command
	 * @param info CommandInformation with syntax, permission etc
	 * @param handler The CommandHandler which handels the execution of the command
	 */
	public void register(CommandInformation info, CommandHandler handler) {
		getCommandRegistry().register(info, handler);
	}
	
	/**
	 * Register your own syntax.
	 * @param method The base command of your syntax
	 * @param handler The handler to check values against your syntax
	 */
	public static void registerSyntax(String method, SyntaxHandler handler) {
		SyntaxValidations.getSyntaxes().put(method, handler);
	}
	
	/**
	 * Register your own PermissionHandler
	 * @param permissionHandler PermissionHandler wich checks whether the sender has permission to execute the command
	 */
	public void registerPermissionHandler(PermissionHandler permissionHandler) {
		getCommandRegistry().setPermissionHandler(permissionHandler);
	}
	
	/**
	 * Register default HelpMap
	 */
	public void registerDefaultHelpMap() {
		getCommandRegistry().registerDefaultHelpMap();
	}
	
	/**
	 * Register own HelpMap
	 * @param helpMap Handles the help-message
	 */
	public void registerHelpMap(HelpMap helpMap) {
		getCommandRegistry().registerHelpMap(helpMap);
	}
	
	/**
	 * Copy InputStream to output file
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	private void copyFile(InputStream in, File out) throws Exception {
        InputStream fis = in;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
