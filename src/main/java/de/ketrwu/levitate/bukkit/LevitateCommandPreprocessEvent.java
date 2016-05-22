package de.ketrwu.levitate.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.ketrwu.levitate.CommandInformation;
import de.ketrwu.levitate.ParameterSet;

public class LevitateCommandPreprocessEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
	private CommandSender sender;
	private CommandInformation commandInformation; 
	private ParameterSet parameterSet;
    private boolean cancelled;
 
    public LevitateCommandPreprocessEvent(CommandSender sender, CommandInformation commandInformation, ParameterSet parameterSet) {
    	this.sender = sender;
    	this.commandInformation = commandInformation;
    	this.parameterSet = parameterSet;
    }
  
    public boolean isCancelled() {
        return cancelled;
    }
 
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public CommandSender getSender() {
		return sender;
	}

	public CommandInformation getCommandInformation() {
		return commandInformation;
	}
	
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
	
}
