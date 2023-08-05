package xyz.mintydev.punishment.core;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public Punishment(PunishmentType type, UUID playerUUID, String playerName, UUID operator, String operatorName, Date startDate, Date endDate, String reason) {
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
		final Date now = new Date();
		return getEndDate().getTime() - now.getTime() < 0;
	}

	@Override
	public String toString() {
		return "Punishment(" + type.toString() + " - " + playerUUID + " - " + playerName + " - " + operatorName.toString() + " - " + startDate.getTime() + " <-> " + endDate.getTime() + " - " + reason + " [ID: " + id + "])";	}
	
	public void execute() {
		execute(false);
	}
	
    public void execute(boolean silent) {
    	/* Set default reason */
    	if(reason.length() == 0) {
    		reason = LangManager.getMessage("default-reason");
    	}
    	
    	/* Add the punishment to the player history */
    	DatabaseManager.get().executeStatement(SQLQuery.INSERT_PUNISHMENT, type.name(), getPlayerUUID().toString(), getPlayerName(), getOperator().toString(), getOperatorName(), getStartDate().getTime(), getEndDate() != null ? getEndDate().getTime() : null, reason);
    	
    	if(type != PunishmentType.KICK) {
    		DatabaseManager.get().executeStatement(SQLQuery.INSERT_PUNISHMENT_HISTORY, type.name(), getPlayerUUID().toString(), getPlayerName(), getOperator().toString(), getOperatorName(), getStartDate().getTime(), getEndDate() != null ? getEndDate().getTime() : null, reason);
    		
    		// Retrieve id
    		try(ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.SELECT_EXACT_PUNISHMENT, getPlayerUUID().toString(), getStartDate().getTime(), getType().name())) {
    			while(rs.next()) {
    				id = rs.getInt("id");
    			}
    		} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	
    	/* Add the punishment to the cache */
    	// Check if player is online
    	if(Bukkit.getPlayer(this.playerUUID) != null && Bukkit.getPlayer(this.playerUUID).isOnline()) {
    		PunishmentManager.get().getLoadedPunishments().add(this);
    		
    		// kick player if online & ban/tempban
    		if(this.type == PunishmentType.BAN || this.type.getBase() == PunishmentType.BAN) {
    			Bukkit.getPlayer(playerName).kickPlayer(this.getKickLayout());
    		}
    	}
    	PunishmentManager.get().getHistoryPunishments().add(this);
    
    	/* Broadcast */
    	if(!silent) {
    		for(String str : LangManager.getMessageList(this.type.getBroadcast())) {
    			str = replaceWithValues(str);
    			Bukkit.broadcastMessage(str);
    		}
    	}
    }
    
    private String replaceWithValues(final String str) {
    	String res = str;
    	res = res.replaceAll("%reason%", this.reason);
    	res = res.replaceAll("%operator%", this.operatorName);
    	res = res.replaceAll("%player%", this.playerName);
    	res = res.replaceAll("%start_date%", CalendarUtil.getFormatted(this.startDate));
		res = res.replaceAll("%id%", (id > 0 ? id+"" : "N/A"));
		
		if(this.type == PunishmentType.TEMP_BAN) {
			res = res.replaceAll("%end_date%", CalendarUtil.getFormatted(this.endDate));
		}
		return res;
    }
    
    public String getKickLayout() {
    	final List<String> base = LangManager.getMessageList(this.type == PunishmentType.TEMP_BAN ? "layouts.banned-temp" : "layouts.banned");
    	
    	String res = "";
    	for(String str : base) {
    		str = replaceWithValues(str);
    		res += str + "\n";
    	}
    	return res;
    }
	
	/* 
	 * Getters & Setters
	 * */
	
	public String getReason() {
		return reason;
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
