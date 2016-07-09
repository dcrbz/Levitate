package de.ketrwu.levitate;

import org.bukkit.command.CommandSender;

import de.ketrwu.levitate.bukkit.LevitateMessagePreprocessEvent;
import de.ketrwu.levitate.handler.MessageHandler;

public class DefaultMessageHandler implements MessageHandler {

	@Override
	public void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(message);
	}

	@Override
	public void sendMessage(LevitateMessagePreprocessEvent event) {
		// TODO Auto-generated method stub
		
	}

}
