package xyz.mintydev.punishment;

import org.bukkit.plugin.java.JavaPlugin;

public class MINTPunishment extends JavaPlugin {

	private static MINTPunishment instance;
	
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
		
	}

	@Override
	public void onDisable() {

	}
	
	/*
	 * Getters & Setters
	 * */
	
	public static MINTPunishment get() {
		return instance;
	}
	
}
