package xyz.mintydev.punishment.util.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;

import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.managers.ConfigManager;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.PunishmentManager;
import xyz.mintydev.punishment.util.PaginationUtil;
import xyz.mintydev.punishment.util.UUIDFetcher;

public class HistoryCommand extends Command {

	public HistoryCommand(String... aliases) {
		super(aliases);
		
		/* Setup command */
		this.setMinArgs(1);
		this.setMaxArgs(2);
		this.addRequirement(new PermissionRequirement("mintpunishment.history"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, String label) throws Exception {
		int pageNumber = 1;
		
		/* Usage : /history <player> [page] */
		
		final String playerName = args[0];
		if(playerName.length() > 16) {
			wrongUsage(sender, label);
			return false;
		}

		final UUID playerUUID = UUIDFetcher.getUUID(playerName);
		
		if(playerUUID == null) {
			sender.sendMessage(LangManager.getMessage("history.no-history").replaceAll("%player%", playerName));
			return false;
		}
		
		if(args.length == 2) {
			try {
				pageNumber = Integer.parseInt(args[1]);
			}catch(Exception e) {
				sender.sendMessage(LangManager.getMessage("history.invalid-page").replaceAll("%player%", playerName));
				return false;
			}
		}
	
		
		// open page pageNumber
		
		final List<Punishment> activePunishments = PunishmentManager.get().getPunishments(playerUUID, null, true);
		List<Punishment> allPunishments = new ArrayList<>();
		allPunishments.addAll(PunishmentManager.get().getPunishments(playerUUID, null, false));
		allPunishments.addAll(activePunishments);
		
		/* Sort by chronological order */
        Collections.sort(allPunishments, new Comparator<Punishment>() {
            @Override
            public int compare(Punishment p1, Punishment p2) {
                return p2.getStartDate().compareTo(p1.getStartDate());
            }
        });
		
		if(allPunishments == null || allPunishments.size() == 0) {
			sender.sendMessage(LangManager.getMessage("history.no-history").replaceAll("%player%", playerName));
			return false;
		}
		
		// do the pagination
		final PaginationUtil<Punishment> pagination = new PaginationUtil<>(allPunishments, ConfigManager.get().getEntriesPerPage());
		
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
					
					if(activePunishments.contains(punishment)) {
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

	@Override
	public void wrongUsage(CommandSender sender, String label) {
		String res = String.format("§cUsage: /" + label + " <player> [page]");
		sender.sendMessage(res);
	}

}