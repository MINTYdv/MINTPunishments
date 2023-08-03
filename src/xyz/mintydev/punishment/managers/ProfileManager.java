package xyz.mintydev.punishment.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.core.PlayerProfile;

public class ProfileManager {

	private Map<Player, PlayerProfile> profiles = new HashMap<>();
	
	private final MINTPunishment main;
	
	public ProfileManager(MINTPunishment main) {
		this.main = main;
	}
	
	/** 
	 * Function called to get the PlayerProfile of a player
	 * 
	 * @param the player to get the profile from
	 * @return a PlayerProfile class with all the players info
	 * */
	public PlayerProfile getProfile(Player player) {
		if(!(profiles.containsKey(player))) {
			// Create a profile for the player
			
			if(!createProfile(player)) {
				main.getLogger().log(Level.SEVERE, "Could not create an account for the player : " + player.getName() + ".");
			}
		}
		
		return profiles.get(player);
	}
	
	/** 
	 * Create a PlayerProfile for a player
	 * 
	 * @param the player to create a profile for
	 * @return true if everything went fine, false if there was an error during the creation process
	 * */
	private boolean createProfile(Player player) {
		if(profiles.containsKey(player)) return false;
		
		PlayerProfile profile = new PlayerProfile(player.getUniqueId());
		
		profiles.put(player, profile);
		return true;
	}
	
	/* 
	 * Getters & Setters
	 * */

	public Map<Player, PlayerProfile> getProfiles() {
		return profiles;
	}
	
}
