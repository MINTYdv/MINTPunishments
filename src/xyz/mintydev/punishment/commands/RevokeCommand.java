package xyz.mintydev.punishment.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;

import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.PunishmentManager;
import xyz.mintydev.punishment.util.UUIDFetcher;
import xyz.mintydev.punishment.util.command.Command;
import xyz.mintydev.punishment.util.command.PermissionRequirement;

public class RevokeCommand extends Command {

	private final String langPath;
	private final PunishmentType type;

	public RevokeCommand(PunishmentType type, String langPath, String... aliases) {
		super(aliases);

		this.addRequirement(new PermissionRequirement(type.getRevokePerm()));
		this.setMinArgs(1);
		this.setMaxArgs(2);
		this.type = type;
		this.langPath = langPath;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args, String label) throws Exception {
		/* Usage : /unban <player> */

		final String playerName = args[0];
		if (playerName.length() > 16) {
			wrongUsage(sender, label);
			return false;
		}

		final UUID playerUUID = UUIDFetcher.getUUID(playerName);

		List<Punishment> punishments = PunishmentManager.get().getPunishments(playerUUID, type, true);
		if (punishments == null || punishments.size() == 0) {
			sender.sendMessage(LangManager.getMessage("punishments.revoke." + langPath + ".no-active").replaceAll("%player%", playerName));
			return false;
		}

		for (Punishment pt : punishments) {
			pt.delete(sender.getName());
		}

		sender.sendMessage(LangManager.getMessage("punishments.revoke." + langPath + ".success").replaceAll("%player%", playerName));
		return true;
	}

	@Override
	public void wrongUsage(CommandSender sender, String label) {
		String res = String.format("§cUsage: /%s %s", label, "<player>");
		sender.sendMessage(res);
	}

	/*
	 * Getters & Setters
	 */

	public PunishmentType getType() {
		return type;
	}

}
