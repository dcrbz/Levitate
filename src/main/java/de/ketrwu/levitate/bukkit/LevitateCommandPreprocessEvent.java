package de.ketrwu.levitate.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import de.ketrwu.levitate.CommandInformation;
import de.ketrwu.levitate.Levitate;
import de.ketrwu.levitate.ParameterSet;

/**
 * Gets called when a user tries to execute a Levitate-Command.<br />
 * When cancelled, Levitate wont execute the command.
 * 
 * @author Kenneth Wussmann
 */
public class LevitateCommandPreprocessEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Plugin plugin;
	private CommandSender sender;
	private CommandInformation commandInformation;
	private ParameterSet parameterSet;
	private boolean cancelled;

	/**
	 * Gets called when a user tries to execute a Levitate-Command.<br />
	 * When cancelled, Levitate wont execute the command.
	 */
	public LevitateCommandPreprocessEvent(Plugin plugin, CommandSender sender, CommandInformation commandInformation,
			ParameterSet parameterSet) {
		this.plugin = plugin;
		this.sender = sender;
		this.commandInformation = commandInformation;
		this.parameterSet = parameterSet;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Cancel the command execution
	 */
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * Get the sender of the command. Console or player
	 * @return
	 */
	public CommandSender getSender() {
		return sender;
	}

	/**
	 * Get the Levitate-Command which would be executed
	 * @return
	 */
	public CommandInformation getCommandInformation() {
		return commandInformation;
	}
	
	/**
	 * Get the parameters the user entered
	 * @return
	 */
	public ParameterSet getParameterSet() {
		return parameterSet;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	/**
	 * Get the Plugin instance of the plugin which triggered this event
	 * @return
	 */
	public Plugin getPlugin() {
		return plugin;
	}
	
}
