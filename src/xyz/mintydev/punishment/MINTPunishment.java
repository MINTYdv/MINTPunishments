package xyz.mintydev.punishment;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.punishment.command.HistoryCommand;
import xyz.mintydev.punishment.command.PunishmentCommand;
import xyz.mintydev.punishment.command.RevokeCommand;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.listeners.ChatListener;
import xyz.mintydev.punishment.listeners.DataListener;
import xyz.mintydev.punishment.managers.ConfigManager;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.PunishmentManager;
import xyz.mintydev.punishment.managers.database.DatabaseManager;

public class MINTPunishment extends JavaPlugin {

	private static MINTPunishment instance;

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
		
		if(isEnabled()) getLogger().info("Plugin enabled. Thanks for choosing MINTPunishment !");
	}
	
	private void registerListeners() {
		if(!isEnabled()) return;
		getServer().getPluginManager().registerEvents(new DataListener(instance), instance);
		getServer().getPluginManager().registerEvents(new ChatListener(instance), instance);
	}

	private void registerCommands() {
		if(!isEnabled()) return;
		
		this.getCommand("ban").setExecutor(new PunishmentCommand("ban", PunishmentType.BAN, PunishmentType.TEMP_BAN, true));
		this.getCommand("blacklist").setExecutor(new PunishmentCommand("blacklist", PunishmentType.BLACKLIST, null, false));
		this.getCommand("mute").setExecutor(new PunishmentCommand("mute", PunishmentType.MUTE, PunishmentType.TEMP_MUTE, true));
		this.getCommand("kick").setExecutor(new PunishmentCommand("kick", PunishmentType.KICK, null, false));
		
		this.getCommand("unmute").setExecutor(new RevokeCommand("unmute", PunishmentType.MUTE, "mute"));
		this.getCommand("unban").setExecutor(new RevokeCommand("unban", PunishmentType.BAN, "ban"));
		this.getCommand("unblacklist").setExecutor(new RevokeCommand("unblacklist", PunishmentType.BLACKLIST, "blacklist"));
		
		this.getCommand("history").setExecutor(new HistoryCommand());
	}

	private void registerManagers() {
		configManager = new ConfigManager(instance);

		langManager = new LangManager(instance);
		databaseManager = new DatabaseManager(instance);
		punishmentManager = new PunishmentManager(instance);
	}

	@Override
	public void onDisable() {
		DatabaseManager.get().shutdown();
		
		getLogger().info("Plugin disabled. Thanks for choosing MINTPunishment !");
	}
	
	/*
	 * Getters & Setters
	 * */
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public PunishmentManager getPunishmentManager() {
		return punishmentManager;
	}
	
	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	public LangManager getLangManager() {
		return langManager;
	}
	
	public static MINTPunishment get() {
		return instance;
	}
	
}
