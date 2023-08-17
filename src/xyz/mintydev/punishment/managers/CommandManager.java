package xyz.mintydev.punishment.managers;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import xyz.mintydev.punishment.commands.HistoryCommand;
import xyz.mintydev.punishment.commands.PunishmentCommand;
import xyz.mintydev.punishment.commands.RevokeCommand;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.util.command.Command;
import xyz.mintydev.punishment.util.command.CommandRequirement;
import xyz.mintydev.punishment.util.command.FakeCommand;

/**
 * Command manager class
 * 
 * @author MINTY
 */
public class CommandManager implements CommandExecutor {

	private Set<Command> commands = new HashSet<>();
	private CommandMap commandMap;

	public CommandManager() {

		retrieveCommandMap();

		addCommand(new PunishmentCommand(PunishmentType.BAN, PunishmentType.TEMP_BAN, true, "ban", "tempban", "tban"));
		addCommand(new PunishmentCommand(PunishmentType.BLACKLIST, null, false, "blacklist", "bl"));
		addCommand(new PunishmentCommand(PunishmentType.MUTE, PunishmentType.TEMP_MUTE, true, "mute", "tempmute", "silence"));
		addCommand(new PunishmentCommand(PunishmentType.KICK, null, false, "kick"));

		addCommand(new RevokeCommand(PunishmentType.MUTE, "mute", "unmute"));
		addCommand(new RevokeCommand(PunishmentType.BAN, "ban", "unban"));
		addCommand(new RevokeCommand(PunishmentType.BLACKLIST, "blacklist", "unblacklist"));

		addCommand(new HistoryCommand("history"));

		registerCommands();
	}

	private void retrieveCommandMap() {
		try {
			Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f.setAccessible(true);
			commandMap = (CommandMap) f.get(Bukkit.getServer());
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public void addCommand(Command cmd) {
		this.commands.add(cmd);
	}

	public void registerCommands() {
		for (Command c : this.getCommands()) {
			for (String l : c.getAliases()) {
				commandMap.register(l, new FakeCommand(l));
			}
		}
	}

	/**
	 * Main Bukkit function that tries all the possible registered commands w/their
	 * aliases
	 */
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		for (Command cmd : commands) {
			if (cmd.getAliases().contains(label)) {
				if (!validRequirements(sender, cmd)) {
					return false;
				}

				if (cmd.getMinArgs() > 0 && (args == null || args.length < cmd.getMinArgs())) {
					cmd.wrongUsage(sender, label);
					return false;
				}
				if (args != null && cmd.getMaxArgs() > 0 && args.length > cmd.getMaxArgs()) {
					cmd.wrongUsage(sender, label);
					return false;
				}

				try {
					cmd.execute(sender, args, label);
				} catch (Exception e) {
					sender.sendMessage(LangManager.getMessage("errors.unknown"));
					e.printStackTrace();
					return false;
				}
			}
		}

		return false;
	}

	/**
	 * Check if the player has all the valid requirements for a certain command
	 * 
	 * @param CommandSender the command sender (console / player)
	 * @param Command       the command object
	 */
	public boolean validRequirements(CommandSender sender, Command cmd) {
		for (CommandRequirement rq : cmd.getRequirements()) {
			if (!rq.isValid(sender)) {
				rq.sendError(sender);
				return false;
			}
		}
		return true;
	}

	/*
	 * Getters & Setters
	 */

	public Set<Command> getCommands() {
		return commands;
	}

}
