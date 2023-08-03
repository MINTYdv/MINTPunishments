package xyz.mintydev.punishment.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;

public class PunishmentManager {

	private final MINTPunishment main;
	private static PunishmentManager instance;
	
	private List<Punishment> punishments = new ArrayList<>();
	private List<String> cachedData = new ArrayList<>();
	
	public PunishmentManager(MINTPunishment main) {
		this.main = main;
		instance = this;
	}

	public boolean tryConnection(UUID uuid) {
		return true;
	}
	
	public List<Punishment> getPunishments(UUID uuid) {
		return null;
	}
	
	public List<Punishment> getActivePunishments(UUID uuid, PunishmentType type){
		return null;
	}
	
	public boolean isCached(String UUID) {
		if(cachedData.contains(UUID)) return true;
		return false;
	}

	/* 
	 * Getters & Setters
	 * */
	
	public static PunishmentManager get() {
		return instance;
	}
	
}
