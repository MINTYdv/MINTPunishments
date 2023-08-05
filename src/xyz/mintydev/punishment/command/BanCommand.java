package xyz.mintydev.punishment.command;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.util.CalendarUtil;

public class BanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// Usage : /ban <player> [time] <reason> [-s]
		
		if(args == null || args.length < 1) {
			wrongUsage(sender);
			return false;
		}
		
		final String playerName = args[0];
		if(playerName.length() > 16) {
			wrongUsage(sender);
			return false;
		}
		
		// Check if tempban
		long totalDuration = -1;
		if(args.length > 1 && CalendarUtil.convertTextToMS(args[1]) > 0) {
			totalDuration = CalendarUtil.convertTextToMS(args[1]);
		}
		
		// Get reason
		String reason = "";
		boolean silent = false;
		
		for(final String arg : args) {
			if(arg.equalsIgnoreCase("-s") || arg.equalsIgnoreCase("-silent")) {
				silent = true;
				continue;
			}
			if(arg.equalsIgnoreCase(playerName)) continue;
			
			if(totalDuration > 0 && arg.equalsIgnoreCase(args[1])) continue;
			reason += arg + " ";
		}
		
		final boolean isPlayer = sender instanceof Player;
		final Date now = new Date();
		
		Punishment pt = new Punishment((totalDuration > 0 ? PunishmentType.TEMP_BAN : PunishmentType.BAN),
				Bukkit.getPlayer(playerName).getUniqueId(),
				playerName,
				isPlayer ? ((Player)sender).getUniqueId() : null,
				sender.getName(),
				now,
				(totalDuration > 0 ? new Date(now.getTime()+totalDuration) : null),
				reason);
	
		pt.execute(silent);
		sender.sendMessage("§aBan correctement appliqué !");
		return true;
	}
	
	private void wrongUsage(CommandSender sender) {
		sender.sendMessage("§cUsage: /ban <player> [time] <reason> [-s]");
	}

}
