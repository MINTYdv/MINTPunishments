package xyz.mintydev.punishment.core;

public enum PunishmentType {

	BAN("Ban", null, false, "mintpunishment.ban.perma"),
	TEMP_BAN("Tempban", BAN, false, "mintpunishment.ban.temp"),
	MUTE("Mute", null, false, "mintpunishment.mute.perm"),
	TEMP_MUTE("Tempmute", MUTE, false, "mintpunishment.mute.temp"),
	WARNING("Warn", null, false, "mintpunishment.warn"),
	KICK("Kick", null, false, "mintpunishment.kick");
	
	private final String name;
	private final PunishmentType base;
	private final boolean temporary;
	private final String perms;
	
	PunishmentType(String name, PunishmentType base, boolean temporary, String perms){
		this.name = name;
		this.base = base;
		this.temporary = temporary;
		this.perms = perms;
	}

	/*
	 * Getters & Setters
	 * */
	
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
