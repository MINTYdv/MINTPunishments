package xyz.mintydev.punishment.core;

public enum PunishmentType {

	BAN("Ban", "ban", null, false, "mintpunishment.ban.perma", "mintpunishment.unban", "punishments.ban"),
	TEMP_BAN("Tempban", "ban", BAN, true, "mintpunishment.ban.temp", "mintpunishment.unban", "punishments.temp-ban"),
	MUTE("Mute", "mute", null, false, "mintpunishment.mute.perm", "mintpunishment.unmute", "punishments.mute"),
	TEMP_MUTE("Tempmute", "mute", MUTE, true, "mintpunishment.mute.temp", "mintpunishment.unmute",
			"punishments.temp-mute"),
	BLACKLIST("Blacklist", "blacklist", null, false, "mintpunishment.blacklist", "mintpunishment.unblacklist",
			"punishments.blacklist"),
	KICK("Kick", "kick", null, false, "mintpunishment.kick", "", "punishments.kick");

	private final String name, applyPerm, revokePerm, langPath, command;
	private final PunishmentType base;
	private final boolean temporary;

	PunishmentType(String name, String command, PunishmentType base, boolean temporary, String applyPerm,
			String revokePerm, String langPath) {
		this.name = name;
		this.command = command;
		this.base = base;
		this.temporary = temporary;
		this.applyPerm = applyPerm;
		this.revokePerm = revokePerm;
		this.langPath = langPath;
	}

	/*
	 * Getters & Setters
	 */

	public String getCommand() {
		return command;
	}

	public String getLangPath() {
		return langPath;
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

	public String getApplyPerm() {
		return applyPerm;
	}

	public String getRevokePerm() {
		return revokePerm;
	}

}
