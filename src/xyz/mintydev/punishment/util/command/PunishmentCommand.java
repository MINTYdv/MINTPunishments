package xyz.mintydev.punishment.util.command;

import java.util.Date;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.util.CalendarUtil;
import xyz.mintydev.punishment.util.TimeUnit;
import xyz.mintydev.punishment.util.UUIDFetcher;

public class PunishmentCommand extends Command {

	private final PunishmentType type, tempType;
	private final boolean takeTime;
	
	public PunishmentCommand(PunishmentType type, PunishmentType tempType, boolean takeTime, String... aliases) {
		super(aliases);
		
		/* Setup the command */
		this.addRequirement(new PermissionRequirement(type.getApplyPerm()));
		this.setMinArgs(1);
		
		/* Setup the punishment */
		this.type = type;
		this.tempType = tempType;
		this.takeTime = takeTime;
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args, String label) throws Exception {
		/* Usage : /ban <player> [time] <reason> [-s] */
		
		final String playerName = args[0];
		if(playerName.length() > 16) {
			wrongUsage(sender, label);
			return false;
		}
		
		// Check if tempban
		long totalDuration = -1;
		if(args.length > 1) {
			final TimeUnit unit = CalendarUtil.getUnit(args[1]);
			if(unit != null) {
				totalDuration = CalendarUtil.getNumberfromUnit(args[1], unit);
			}
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
			
			if(totalDuration > 0 && arg.equalsIgnoreCase(args[1]) && takeTime) continue;
			reason += arg + " ";
		}
		
		if(reason.length() >= 255) {
			sender.sendMessage(LangManager.getMessage("errors.reason-too-long"));
			return false;
		}
		
		final boolean isPlayer = sender instanceof Player;
		final Date now = new Date();
		
		final UUID playerUUID = UUIDFetcher.getUUID(playerName);
		
		PunishmentType ptype = type;
		if(takeTime && totalDuration > 0) {
			ptype = tempType;
		}
		
		Punishment pt = new Punishment(ptype,
				playerUUID,
				playerName,
				isPlayer ? ((Player)sender).getUniqueId() : null,
				sender.getName(),
				now,
				(totalDuration > 0 ? new Date(now.getTime()+totalDuration) : null),
				reason);
	
		pt.execute(silent);
		
		final String applyMsg = pt.replaceWithValues(LangManager.getMessage(totalDuration > 0 ? this.tempType.getLangPath() + ".apply-message": this.type.getLangPath() + ".apply-message"));
		sender.sendMessage(applyMsg);
		return true;
	}

	
	@Override
	public void wrongUsage(CommandSender sender, String label) {
		String res = String.format("§cUsage: /%s %s", label, "<player>" + (takeTime ? " [time]" : "") + " [reason] [-s]");
		sender.sendMessage(res);
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public PunishmentType getType() {
		return type;
	}
	
	public boolean doTakeTime() {
		return takeTime;
	}

}