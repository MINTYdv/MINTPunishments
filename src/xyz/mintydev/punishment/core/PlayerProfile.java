package xyz.mintydev.punishment.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerProfile {

	private final UUID uuid;
	private List<Punishment> punishments = new ArrayList<>();
	
	public PlayerProfile(UUID uuid) {
		this.uuid = uuid;
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
