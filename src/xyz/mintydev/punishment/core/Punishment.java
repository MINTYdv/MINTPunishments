package xyz.mintydev.punishment.core;

import java.util.Date;
import java.util.UUID;

public class Punishment {

	private final PunishmentType type;
	
	private final UUID playerUUID;
	private final String playerName;
	private final UUID operator;
	
	private final Date startDate;
	private final Date endDate;
	private final String reason;
	private final int id;
	
	public Punishment(PunishmentType type, UUID playerUUID, String playerName, UUID operator, Date startDate, Date endDate, long duration, String reason, int id) {
		this.type = type;
		this.playerUUID = playerUUID;
		this.playerName = playerName;
		this.operator = operator;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.id = id;
	}
	
	public boolean isExpired() {
		final Date now = new Date();
		return getEndDate().getTime() - now.getTime() < 0;
	}

	/* 
	 * Getters & Setters
	 * */
	
	public String getReason() {
		return reason;
	}
	
	public int getId() {
		return id;
	}
	
	public UUID getOperator() {
		return operator;
	}
	
	public PunishmentType getType() {
		return type;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public UUID getPlayerUUID() {
		return playerUUID;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
}
