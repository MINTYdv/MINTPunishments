package xyz.mintydev.punishment.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.PunishmentManager;
import xyz.mintydev.punishment.util.PaginationUtil;
import xyz.mintydev.punishment.util.UUIDFetcher;

public class HistoryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		int pageNumber = 1;
		
		// Usage : /history <player> [page]
		
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
		
		if(playerUUID == null) {
			sender.sendMessage(LangManager.getMessage("history.no-history").replaceAll("%player%", playerName));
			return false;
		}
		
		if(args.length >= 2) {
			try {
				pageNumber = Integer.parseInt(args[1]);
			}catch(Exception e) {
				sender.sendMessage(LangManager.getMessage("history.invalid-page").replaceAll("%player%", playerName));
				return false;
			}
		}
	
		
		// open page pageNumber
		
		List<Punishment> expired = PunishmentManager.get().getPunishments(playerUUID, null, false);
		List<Punishment> punishments = expired;
		punishments.addAll(PunishmentManager.get().getPunishments(playerUUID, null, true));

		if(punishments == null || punishments.size() == 0) {
			sender.sendMessage(LangManager.getMessage("history.no-history").replaceAll("%player%", playerName));
			return false;
		}
		
		// do the pagination
		PaginationUtil<Punishment> pagination = new PaginationUtil<>(punishments, 10);
		
		if(pagination.getContents(pageNumber) == null || pagination.getContents(pageNumber).size() == 0) {
			sender.sendMessage(LangManager.getMessage("history.invalid-page").replaceAll("%player%", playerName));
			return false;
		}
		
		List<String> res = new ArrayList<>();
		for(String str : LangManager.getMessageList("history.history-format")) {
			str = str.replaceAll("%page%", pageNumber+"");
			str = str.replaceAll("%player%", playerName);
			str = str.replaceAll("%maxpage%", pagination.getPagesAmount()+"");
			
			if(str.toLowerCase().contains("%content%")) {
				for(final Punishment punishment : pagination.getContents(pageNumber)) {
					
					String pLine = LangManager.getMessage("history.history-line");
					pLine = punishment.replaceWithValues(pLine);
					
					if(!punishment.getType().isTemporary()) {
						pLine = pLine.replaceAll("%duration%", "Permanent");
					}
					
					if(!expired.contains(punishment)) {
						pLine += " " + LangManager.getMessage("history.active");
					}
					
					res.add(pLine);
				}
			} else {
				res.add(str);
			}
		}
		
		for(String str : res) sender.sendMessage(str);
		return true;
	}
	
	private void wrongUsage(CommandSender sender) {
		String res = String.format("§cUsage: /history <player> [page]");
		sender.sendMessage(res);
	}

}
