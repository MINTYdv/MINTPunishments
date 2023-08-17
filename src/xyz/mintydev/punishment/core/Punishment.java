package xyz.mintydev.punishment.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.PunishmentManager;
import xyz.mintydev.punishment.managers.database.DatabaseManager;
import xyz.mintydev.punishment.managers.database.SQLQuery;
import xyz.mintydev.punishment.util.CalendarUtil;

public class Punishment {

	private final PunishmentType type;

	private final UUID playerUUID;
	private final String playerName;
	private final UUID operator;
	private final String operatorName;

	private final Date startDate;
	private final Date endDate;
	private String reason;
	private int id;

	public Punishment(PunishmentType type, UUID playerUUID, String playerName, UUID operator, String operatorName,
			Date startDate, Date endDate, String reason) {
		this.type = type;
		this.playerUUID = playerUUID;
		this.playerName = playerName;
		this.operator = operator;
		this.operatorName = operatorName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
	}

	public boolean isExpired() {
		if (getEndDate() == null || getEndDate().getTime() <= 0)
			return false;
		final Date now = new Date();
		return getEndDate().getTime() - now.getTime() < 0;
	}

	@Override
	public String toString() {
		return "Punishment(" + type.name() + " - " + playerUUID + " - " + playerName + " by : "
				+ operatorName.toString() + " duration : " + startDate.getTime() + " <-> "
				+ (endDate != null ? endDate.getTime() : "N/A") + " - " + reason + " [ID: " + id + "])";
	}

	public void execute() {
		execute(false);
	}

	/**
	 * Delete the punishment from the cache and the database
	 * 
	 * @param String the name of the operator (CONSOLE or player username)
	 */
	public void delete(String operator) {

		DatabaseManager.get().executeStatement(SQLQuery.DELETE_PUNISHMENT, getId());

		/* Remove punishment from cache */
		PunishmentManager.get().getLoadedPunishments().remove(this);
	}

	/**
	 * Execute the punishment with all set parameters
	 * 
	 * @param boolean silent? (if silent, no broadcast will be shown)
	 */
	public void execute(boolean silent) {
		if (reason.length() == 0) {
			reason = LangManager.getMessage("default-reason");
		}

		DatabaseManager.get().executeStatement(SQLQuery.INSERT_PUNISHMENT_HISTORY, type.name(),
				getPlayerUUID().toString(), getPlayerName(), getOperator() == null ? null : getOperator().toString(),
				getOperatorName(), getStartDate().getTime(), getEndDate() != null ? getEndDate().getTime() : null,
				reason);

		if (type != PunishmentType.KICK) {
			DatabaseManager.get().executeStatement(SQLQuery.INSERT_PUNISHMENT, type.name(), getPlayerUUID().toString(),
					getPlayerName(), getOperator() == null ? null : getOperator().toString(), getOperatorName(),
					getStartDate().getTime(), getEndDate() != null ? getEndDate().getTime() : null, reason);

			// Retrieve id
			try (ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.SELECT_EXACT_PUNISHMENT,
					getPlayerUUID().toString(), getStartDate().getTime(), getType().name())) {
				while (rs.next()) {
					id = rs.getInt("id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (PunishmentManager.get().isCached(playerUUID.toString())) {
			PunishmentManager.get().getLoadedPunishments().add(this);

			if (this.type == PunishmentType.BAN || this.type == PunishmentType.BLACKLIST
					|| this.type.getBase() == PunishmentType.BAN || this.type == PunishmentType.KICK) {
				Bukkit.getPlayer(playerName).kickPlayer(this.getKickLayout());
			}
		}
		PunishmentManager.get().getHistoryPunishments().add(this);

		if (!silent) {
			for (String str : LangManager.getMessageList(this.type.getLangPath() + ".broadcast")) {
				str = replaceWithValues(str);
				Bukkit.broadcastMessage(str);
			}
		}
	}

	/**
	 * Replaces a string placeholders with the values from the punishment object
	 * parameters.
	 * 
	 * @param String the string where to check for placeholders
	 * @return String the modified string with all found placeholders replaced with
	 *         values
	 */
	public String replaceWithValues(final String str) {
		String res = str;
		res = res.replaceAll("%type%", this.type.getName());
		res = res.replaceAll("%reason%", this.reason);
		res = res.replaceAll("%operator%", this.operatorName);
		res = res.replaceAll("%player%", this.playerName);
		res = res.replaceAll("%start_date%", CalendarUtil.getFormatted(this.startDate));
		res = res.replaceAll("%id%", (id > 0 ? id + "" : "N/A"));

		if (this.type.isTemporary()) {
			res = res.replaceAll("%end_date%", CalendarUtil.getFormatted(this.endDate));
			final long duration = endDate.getTime() - startDate.getTime();
			res = res.replaceAll("%duration%", CalendarUtil.getDurationFormatted(duration));
			final long remaining = endDate.getTime() - new Date().getTime();
			res = res.replaceAll("%timeleft%", CalendarUtil.getDurationFormatted(remaining));
		}
		return res;
	}

	/**
	 * Returns the kick layout of this punishment (for bans, kick or blacklists)
	 * 
	 * @return the kick layout in one line, spaced with \n and ready to use in the
	 *         kick function
	 */
	public String getKickLayout() {
		List<String> base = new ArrayList<>();

		if (this.type == PunishmentType.KICK) {
			base.addAll(LangManager.getMessageList("layouts.kick"));
		} else if (this.type == PunishmentType.BLACKLIST) {
			base.addAll(LangManager.getMessageList("layouts.blacklisted"));
		} else {
			base.addAll(LangManager
					.getMessageList(this.type == PunishmentType.TEMP_BAN ? "layouts.banned-temp" : "layouts.banned"));
		}

		String res = "";
		for (String str : base) {
			str = replaceWithValues(str);
			res += str + "\n";
		}
		return res;
	}

	/*
	 * Getters & Setters
	 */

	public String getReason() {
		return reason;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperatorName() {
		return operatorName;
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
