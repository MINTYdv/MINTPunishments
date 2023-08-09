package xyz.mintydev.punishment.managers;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.util.command.Command;

public class CommandManager implements CommandExecutor {

	private final MINTPunishment main;
	private Set<Command> commands = new HashSet<>();
	
	public CommandManager(MINTPunishment main) {
		this.main = main;
	
		
	}
	
	public void registerCommands() {
//		for (Command c : this.getCommands()) {
//			for (String l : c.getAliases()) {
//				pl.getCommand(l).setExecutor(this);
//			}
//		}
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
