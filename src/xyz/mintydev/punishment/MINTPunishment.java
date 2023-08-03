package xyz.mintydev.punishment;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.punishment.listeners.DataListener;
import xyz.mintydev.punishment.managers.ConfigManager;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.ProfileManager;
import xyz.mintydev.punishment.managers.PunishmentManager;
import xyz.mintydev.punishment.managers.database.DatabaseManager;

public class MINTPunishment extends JavaPlugin {

	private static MINTPunishment instance;
	
	private ProfileManager profileManager;
	private LangManager langManager;
	private DatabaseManager databaseManager;
	private ConfigManager configManager;
	private PunishmentManager punishmentManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		saveDefaultConfig();
		registerManagers();
		registerCommands();
		registerListeners();
		
		getLogger().info("Plugin enabled. Thanks for choosing MINTPunishment !");
	}
	
	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new DataListener(instance), instance);
	}

	private void registerCommands() {
		
	}

	private void registerManagers() {
		configManager = new ConfigManager(instance);
		
		profileManager = new ProfileManager(instance);
		langManager = new LangManager(instance);
		databaseManager = new DatabaseManager(instance);
		punishmentManager = new PunishmentManager(instance);
	}

	@Override
	public void onDisable() {
		getLogger().info("Plugin disabled. Thanks for choosing MINTPunishment !");
	}
	
	/*
	 * Getters & Setters
	 * */
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	public LangManager getLangManager() {
		return langManager;
	}
	
	public ProfileManager getProfileManager() {
		return profileManager;
	}
	
	public static MINTPunishment get() {
		return instance;
	}
	
}
