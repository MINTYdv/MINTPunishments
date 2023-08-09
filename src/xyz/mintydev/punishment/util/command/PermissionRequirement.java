package xyz.mintydev.punishment.util.command;

import org.bukkit.command.CommandSender;

public class PermissionRequirement extends CommandRequirement {

	private final String permission;
	
	public PermissionRequirement(String permission) {
		this.permission = permission;
	}
	
	@Override
	public boolean isValid(CommandSender sender) {
		return sender.hasPermission(permission);
	}

}
