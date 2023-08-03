package xyz.mintydev.punishment.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

public class PlayerProfile {

	private final UUID uuid;
	private List<Punishment> punishments = new ArrayList<>();
	
	public PlayerProfile(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean isOnline() {
		return Bukkit.getPlayer(uuid) != null;
	}
	
	/* 
	 * Getters & Setters
	 * */

	public UUID getUuid() {
		return uuid;
	}
	
	public List<Punishment> getPunishments() {
		return punishments;
	}
	
}
