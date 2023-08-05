package xyz.mintydev.punishment.core;

public enum PunishmentType {

	BAN("Ban", null, false, "mintpunishment.ban.perma", "broadcast.ban"),
	TEMP_BAN("Tempban", BAN, false, "mintpunishment.ban.temp", "broadcast.temp-ban"),
	MUTE("Mute", null, false, "mintpunishment.mute.perm", "broadcast.mute"),
	TEMP_MUTE("Tempmute", MUTE, false, "mintpunishment.mute.temp", "broadcast.temp-mute"),
	KICK("Kick", null, false, "mintpunishment.kick", "broadcast.kick");
	
	private final String name, perms, broadcast;
	private final PunishmentType base;
	private final boolean temporary;
	
	PunishmentType(String name, PunishmentType base, boolean temporary, String perms, String broadcast){
		this.name = name;
		this.base = base;
		this.temporary = temporary;
		this.perms = perms;
		this.broadcast = broadcast;
	}

	/*
	 * Getters & Setters
	 * */
	
	public String getBroadcast() {
		return broadcast;
	}
	
	public String getName() {
		return name;
	}

	public PunishmentType getBase() {
		return base == null ? this : base;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public String getPerms() {
		return perms;
	}
	
	
	
}
