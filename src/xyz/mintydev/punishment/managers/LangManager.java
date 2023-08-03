package xyz.mintydev.punishment.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import xyz.mintydev.punishment.MINTPunishment;

public class LangManager {

	private final MINTPunishment main;
	
	private static File customConfigFile;
	private static FileConfiguration customConfig;
	
	private final String fileName = "messages.yml";
	
	public LangManager(MINTPunishment main)
	{
		this.main = main;
		createCustomConfig();
	}

	public static String getMessage(final String KEY) {
		return getCustomConfig().getString(KEY).replaceAll("&", "§");
	}
	
	public static List<String> getMessageList(final String KEY)
	{
		List<String> result = new ArrayList<>();
		for(String key : getCustomConfig().getStringList(KEY)) {
			result.add(key.replaceAll("&", "§"));
		}
		return result;
	}
	
    private void createCustomConfig() {
        customConfigFile = new File(main.getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            main.saveResource(fileName, false);
         }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    /* Getters & Setters */
    
    public static FileConfiguration getCustomConfig() {
		return customConfig;
	}
    
    public static File getCustomConfigFile() {
		return customConfigFile;
	}	
}
