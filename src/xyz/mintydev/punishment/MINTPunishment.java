package xyz.mintydev.punishment;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.mintydev.punishment.listeners.ChatListener;
import xyz.mintydev.punishment.listeners.DataListener;
import xyz.mintydev.punishment.managers.CommandManager;
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
	private CommandManager commandManager;
	
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
		getServer().getPluginManager().registerEvents(new DataListener(), instance);
		getServer().getPluginManager().registerEvents(new ChatListener(), instance);
	}

	private void registerCommands() {
		commandManager.registerCommands();
	}

	private void registerManagers() {
		configManager = new ConfigManager(instance);

		langManager = new LangManager(instance);
		databaseManager = new DatabaseManager(instance);
		punishmentManager = new PunishmentManager(instance);
		commandManager = new CommandManager(instance);
	}

	@Override
	public void onDisable() {
		DatabaseManager.get().shutdown();
		
		getLogger().info("Plugin disabled. Thanks for choosing MINTPunishment !");
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
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
