package xyz.mintydev.punishment.managers;

import org.bukkit.configuration.ConfigurationSection;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.managers.database.DatabaseCredentials;

public class ConfigManager {

	private final MINTPunishment main;
	private static ConfigManager instance;
	
	public ConfigManager(MINTPunishment main) {
		this.main = main;
		instance = this;
	}
	
	/** 
	 * Function called to load database credentials from the config.yml file
	 * */
	public DatabaseCredentials loadCredentials() {
		final ConfigurationSection sec = main.getConfig().getConfigurationSection("mysql");
		final String ip = sec.getString("ip"); 
		final String database = sec.getString("database"); 
		final String user = sec.getString("user"); 
		final String password = sec.getString("password"); 
		
		final int port = sec.getInt("port");
		
		return new DatabaseCredentials(ip, database, user, password, port);
	}
	
	public String getDateFormat() {
		if(main.getConfig().getString("date-format") == null || main.getConfig().getString("date-format").length() > 0) return "dd/MM/yyyy HH:mm:ss";
		return main.getConfig().getString("date-format");
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public static ConfigManager get() {
		return instance;
	}
	
}
