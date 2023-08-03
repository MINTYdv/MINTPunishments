package xyz.mintydev.punishment;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.ProfileManager;
import xyz.mintydev.punishment.managers.database.DatabaseManager;

public class MINTPunishment extends JavaPlugin {

	private static MINTPunishment instance;
	
	private ProfileManager profileManager;
	private LangManager langManager;
	private DatabaseManager databaseManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		registerManagers();
		registerCommands();
		registerListeners();
	}
	
	private void registerListeners() {
		
	}

	private void registerCommands() {
		
	}

	private void registerManagers() {
		profileManager = new ProfileManager(instance);
		langManager = new LangManager(instance);
		databaseManager = new DatabaseManager(instance);
	}

	@Override
	public void onDisable() {

	}
	
	/*
	 * Getters & Setters
	 * */
	
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
