package xyz.mintydev.punishment.util.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandSender;

public abstract class Command {

	private Set<String> aliases = new HashSet<String>();
	private ArrayList<CommandRequirement> requirements = new ArrayList<>();
	private int minArgs = -1;
	private int maxArgs = -1;
	
	public Command(String... aliases) {
		for(String al : aliases) addAlias(al);
	}
	
	/** 
	 * Code ran when the command is executed by a player (or the console)
	 * 
	 * @param CommandSender the command sender
	 * @param String[] the command arguments
	 * */
	public abstract boolean execute(CommandSender sender, String[] args, String label) throws Exception;

	/** 
	 * Function called when the player has provided a wrong command usage
	 * 
	 * @param CommandSender sender
	 * @param String the command label
	 * */
	public abstract void wrongUsage(CommandSender sender, String label);
	
	/* 
	 * Functions made to update command parameters
	 * */
	
	public void addAlias(String alias) {
		this.aliases.add(alias);
	}
	
	public void addRequirement(CommandRequirement req) {
		this.requirements.add(req);
	}
	
	public void setMinArgs(int minArgs) {
		this.minArgs = minArgs;
	}
	
	public void setMaxArgs(int maxArgs) {
		this.maxArgs = maxArgs;
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public int getMinArgs() {
		return minArgs;
	}
	
	public int getMaxArgs() {
		return maxArgs;
	}
	
	public Set<String> getAliases() {
		return aliases;
	}
	
	public ArrayList<CommandRequirement> getRequirements() {
		return requirements;
	}
	
}
