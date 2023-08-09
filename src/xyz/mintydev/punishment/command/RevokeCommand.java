package xyz.mintydev.punishment.command;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.PunishmentManager;
import xyz.mintydev.punishment.util.UUIDFetcher;

public class RevokeCommand implements CommandExecutor {

	private final String id, langPath;
	private final PunishmentType type;
	
	public RevokeCommand(String id, PunishmentType type, String langPath) {
		this.id = id;
		this.type = type;
		this.langPath = langPath;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// Usage : /unban <player>
		
		if(!sender.hasPermission(type.getRevokePerm())) {
			sender.sendMessage(LangManager.getMessage("errors.no-permission").replaceAll("%perm%", type.getRevokePerm()));
			return false;
		}
		
		if(args == null || args.length < 1) {
			wrongUsage(sender);
			return false;
		}
		
		final String playerName = args[0];
		if(playerName.length() > 16) {
			wrongUsage(sender);
			return false;
		}

		final UUID playerUUID = UUIDFetcher.getUUID(playerName);
		
		List<Punishment> punishments = PunishmentManager.get().getPunishments(playerUUID, type, true);
		if(punishments == null || punishments.size() == 0) {
			sender.sendMessage(LangManager.getMessage("punishments.revoke." + langPath + ".no-active").replaceAll("%player%", playerName));
			return false;
		}
		
		for(Punishment pt : punishments) {
			pt.delete(sender.getName());
		}
		
		sender.sendMessage(LangManager.getMessage("punishments.revoke." + langPath + ".success").replaceAll("%player%", playerName));
		return true;
	}
	
	private void wrongUsage(CommandSender sender) {
		String res = String.format("§cUsage: /%s %s", id, "<player>");
		sender.sendMessage(res);
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public String getId() {
		return id;
	}
	
	public PunishmentType getType() {
		return type;
	}

}
