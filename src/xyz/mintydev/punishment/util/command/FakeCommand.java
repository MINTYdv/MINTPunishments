package xyz.mintydev.punishment.util.command;

import org.bukkit.command.CommandSender;

import xyz.mintydev.punishment.MINTPunishment;

public class FakeCommand extends org.bukkit.command.Command {

	public FakeCommand(String name) {
		super(name);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		return MINTPunishment.get().getCommandManager().onCommand(sender, this, commandLabel, args);
	}

}
