package de.ketrwu.levitate.bukkit;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.ketrwu.levitate.CommandInformation;
import de.ketrwu.levitate.Levitate;
import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.ParameterSet;
import de.ketrwu.levitate.handler.CommandHandler;

public class LevitatePlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		File globalMessages = new File("plugins/Levitate");
		if(!globalMessages.exists()) globalMessages.mkdirs();
		globalMessages = new File("plugins/Levitate/messages.yml");
		if(!globalMessages.exists()) {
			try {
				globalMessages.createNewFile();
			} catch (IOException e) {
				
			}
		}		
		
		Message.loadConfig(globalMessages);
		
		Levitate levitate = new Levitate(this, false);
		
		levitate.register(new CommandInformation("?levitate <eqi[reload]>", "levitate.reload", "Reload the Levitate messages", "/levitate reload"), new CommandHandler() {
			
			@Override
			public void execute(CommandSender sender, String command, ParameterSet args) {
				Message.reloadConfig(new File("plugins/Levitate/messages.yml"));
				sender.sendMessage(ChatColor.GREEN + "The messages have been reloaded!");
			}
			
		});

		levitate.register(new CommandInformation("?levitate <eqi[version]>", "levitate.version", "Show the running Levitate version", "/levitate version"), new CommandHandler() {
			
			@Override
			public void execute(CommandSender sender, String command, ParameterSet args) {
				sender.sendMessage(ChatColor.GREEN + "You are running Levitate " + ChatColor.GOLD + "v" + getDescription().getVersion() + ChatColor.GREEN + "!");
			}
			
		});
	}
	
}
