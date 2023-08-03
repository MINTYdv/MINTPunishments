package xyz.mintydev.punishment.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.core.PlayerProfile;

public class ProfileManager {

	private Map<UUID, PlayerProfile> profiles = new HashMap<>();
	
	private final MINTPunishment main;
	
	public ProfileManager(MINTPunishment main) {
		this.main = main;
		
		loadAllExistingProfiles();
	}
	
	private void loadAllExistingProfiles() {
		
	}
	
	/** 
	 * Function called to get the PlayerProfile of a player
	 * 
	 * @param the player to get the profile from
	 * @return a PlayerProfile class with all the players info
	 * */
	public PlayerProfile getProfile(UUID uuid) {
		if(!(profiles.containsKey(uuid))) {
			// Create a profile for the player
			
			if(!createProfile(uuid)) {
				main.getLogger().log(Level.SEVERE, "Could not create an account for the player : " + uuid + ".");
			}
		}
		
		return profiles.get(uuid);
	}
	
	/** 
	 * Create a PlayerProfile for a player
	 * 
	 * @param the player to create a profile for
	 * @return true if everything went fine, false if there was an error during the creation process
	 * */
	private boolean createProfile(UUID uuid) {
		if(profiles.containsKey(uuid)) return false;
		
		PlayerProfile profile = new PlayerProfile(uuid);
		
		profiles.put(uuid, profile);
		return true;
	}
	
	/* 
	 * Getters & Setters
	 * */

	public Map<UUID, PlayerProfile> getProfiles() {
		return profiles;
	}
	
}
