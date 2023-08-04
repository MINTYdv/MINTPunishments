package xyz.mintydev.punishment.managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.managers.database.DatabaseManager;
import xyz.mintydev.punishment.managers.database.SQLQuery;

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
	
	public List<Punishment> getPunishments(UUID uuid, PunishmentType type, boolean current){
		List<Punishment> res = new ArrayList<>();
		List<Punishment> toCheck = new ArrayList<>();
		
		if(isCached(uuid.toString())) {
			
			for(Punishment p : punishments) {
				if(!(p.getPlayerUUID().equals(uuid))) continue;
				toCheck.add(p);
			}
			
			return res;
		} else {
			// query database
			try(ResultSet rSet = DatabaseManager.get().executeResultStatement(current ? SQLQuery.SELECT_USER_PUNISHMENTS_UUID : SQLQuery.SELECT_USER_PUNISHMENTS_HISTORY_UUID, uuid.toString())) {
				while(rSet.next()) {
					final Punishment dbPunishment = DatabaseManager.get().getPunishmentFromResultSet(rSet);
					if(dbPunishment == null) continue;
					toCheck.add(dbPunishment);
				}
			}catch(Exception e) {
				main.getLogger().log(Level.SEVERE, "Error while trying to fetch " + (current ? "active" : "expired") + " punishments for : " + uuid.toString() + ". Check database connection.");
				e.printStackTrace();
			}
		}
		
		// Filter punishments to correspond to criterias (X type only & active only)
		for(Punishment p : toCheck) {
			if(current && p.isExpired()) continue;
			if(type != null && p.getType() != type) continue;
			res.add(p);
		}
		
		return res;
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
