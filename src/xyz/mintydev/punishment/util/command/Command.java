package xyz.mintydev.punishment.util.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

public abstract class Command {

	private ArrayList<String> aliases = new ArrayList<String>();
	private ArrayList<CommandRequirement> requirements = new ArrayList<>();
	private String syntax;
	
	public abstract boolean execute(CommandSender sender, String[] args) throws Exception;
	
}
