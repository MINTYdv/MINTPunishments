package xyz.mintydev.punishment.core;

import java.util.Date;
import java.util.UUID;

public class Punishment {

	private UUID playerUUID;
	private Date startDate;
	private long duration;

	public Punishment(UUID playerUUID, Date startDate, long duration) {
		this.playerUUID = playerUUID;
		this.startDate = startDate;
		this.duration = duration;
	}
	
	public Date getExpirationDate() {
		return new Date(startDate.getTime() + duration);
	}
	
	public boolean isExpired() {
		final Date now = new Date();
		
		return getExpirationDate().getTime() - now.getTime() < 0;
	}

	/* 
	 * Getters & Setters
	 * */
	
	public UUID getPlayerUUID() {
		return playerUUID;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public long getDuration() {
		return duration;
	}
	
}
