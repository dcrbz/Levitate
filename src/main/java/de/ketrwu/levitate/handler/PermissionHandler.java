package de.ketrwu.levitate.handler;

import org.bukkit.command.CommandSender;

/**
 * Check if sender has the given permission
 *
 * @author Kenneth Wussmann
 */
public interface PermissionHandler {

    /**
     * Check if sender has the given permission
     *
     * @param sender     The sender, in Bukkit/Spigot plugins it's CommandSender
     * @param permission The permission
     * @return Return true if the sender has permission
     */
    public boolean hasPermission(CommandSender sender, String permission);

}
