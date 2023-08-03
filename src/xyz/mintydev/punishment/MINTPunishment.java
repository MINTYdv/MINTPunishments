package xyz.mintydev.punishment;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.ProfileManager;

public class MINTPunishment extends JavaPlugin {

	private static MINTPunishment instance;
	
	private ProfileManager profileManager;
	private LangManager langManager;
	
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
	}

	@Override
	public void onDisable() {

	}
	
	/*
	 * Getters & Setters
	 * */
	
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
