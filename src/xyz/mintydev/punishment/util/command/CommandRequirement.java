package xyz.mintydev.punishment.util.command;

import org.bukkit.command.CommandSender;

public abstract class CommandRequirement {

	public abstract boolean isValid(CommandSender sender);

	public abstract void sendError(CommandSender sender);
	
}
