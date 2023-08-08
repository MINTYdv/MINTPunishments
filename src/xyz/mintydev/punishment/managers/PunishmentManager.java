package xyz.mintydev.punishment.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.core.PlayerProfile;
import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.core.PunishmentType;
import xyz.mintydev.punishment.managers.database.DatabaseManager;
import xyz.mintydev.punishment.managers.database.SQLQuery;

public class PunishmentManager {

	private final MINTPunishment main;
	private static PunishmentManager instance;
	
	private Set<Punishment> punishments = new HashSet<>();
	private Set<Punishment> historyPunishments = new HashSet<>();
	private Set<String> cachedData = new HashSet<>();
	
	public PunishmentManager(MINTPunishment main) {
		this.main = main;
		instance = this;
		
		// In case of reloads, put online players into cache
		for(Player player : Bukkit.getOnlinePlayers()) {
        	final String connectionResult = PunishmentManager.get().tryConnection(player.getName(), player.getUniqueId());
        	if(connectionResult != null) {
        		player.kickPlayer(connectionResult);
        	}
		}
	}

	/** 
	 * Function called when the player tries to connect to the server
	 * 
	 * @param NAME the player username
	 * @param UUID the player UUID
	 * @return null if connection is OK, or the ban layout
	 * */
	public String tryConnection(String name, UUID uuid) {
		final PlayerProfile profile = load(name, uuid);
		
		final Punishment ban = profile.getBan();
		
		if(ban == null) {
			// No ban, add the profile to the cache
			profile.acceptLoad();
			return null;
		}
		
		return ban.getKickLayout();
	}
	
	/** 
	 * Function called when the player tries to chat in the server
	 * 
	 * @param NAME the player username
	 * @param UUID the player UUID
	 * @return null if connection is OK, or the ban layout
	 * */
	public boolean tryChat(Player player) {

		final Punishment mute = getMute(player.getUniqueId());
	
		if(mute != null) {
			
			final boolean isTemp = mute.getType() == PunishmentType.TEMP_MUTE;

			for(String str : LangManager.getMessageList(isTemp ? "layouts.temp-mute" : "layouts.mute")) {
				player.sendMessage(mute.replaceWithValues(str));
			}
			return false;
		}
		
		return true;
	}
	
	
    /**
     * Get a players active mute.
     *
     * @param uuid the players uuid
     * @return the mute or <code>null</code> if not muted
     */
    public Punishment getMute(UUID uuid) {
        List<Punishment> punishments = getPunishments(uuid, PunishmentType.MUTE, true);
        return punishments.isEmpty() ? null : punishments.get(0);
    }
	
	/** 
	 * Function called to load the player data and add it to the cache
	 * 
	 * @param name the player username
	 * @param uuid the player UUID
	 * @return a PlayerProfile object containing all player data retrieved from DB
	 * */
	public PlayerProfile load(String name, UUID uuid) {
		Set<Punishment> punishments = new HashSet<>();
		Set<Punishment> history = new HashSet<>();

		try(ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.SELECT_USER_PUNISHMENTS_UUID, uuid.toString());
				ResultSet rsHistory = DatabaseManager.get().executeResultStatement(SQLQuery.SELECT_USER_PUNISHMENTS_UUID, uuid.toString())) {
			
			// Could not retrieve anything from DB
			if(rs == null || rsHistory == null) {
				return null;
			}
			
			while(rs.next()) {
				final Punishment pt = DatabaseManager.get().getPunishmentFromResultSet(rs);
				punishments.add(pt);
			}
			while(rsHistory.next()) {
				final Punishment pt = DatabaseManager.get().getPunishmentFromResultSet(rsHistory);
				history.add(pt);
			}
			
		}catch(SQLException e) {
			main.getLogger().log(Level.SEVERE, "Error while loading data to cache for player : " + name + ". Check database connection.");
			e.printStackTrace();
		}
		
		return new PlayerProfile(name, uuid.toString(), punishments, history);
	}
	
	public List<Punishment> getPunishments(UUID uuid, PunishmentType type, boolean current){
		List<Punishment> res = new ArrayList<>();
		List<Punishment> toCheck = new ArrayList<>();
		
		if(isCached(uuid.toString())) {
			for(Punishment p : getLoadedPunishments()) {
				if(!(p.getPlayerUUID().equals(uuid))) continue;
				toCheck.add(p);
			}
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
			if(type != null) {
				// select by type
				if(p.getType() != type && p.getType().getBase() != type) {
					continue;
				}
			}
			res.add(p);
		}
		return res;
	}
	
	public void removeCache(String name, UUID uuid) {
		if(this.cachedData.contains(name)) cachedData.remove(name);
		if(this.cachedData.contains(uuid.toString())) cachedData.remove(uuid.toString());
		
        Iterator<Punishment> iterator = punishments.iterator();
        while (iterator.hasNext()) {
            Punishment punishment = iterator.next();
            if (punishment.getPlayerUUID().equals(uuid)) {
                iterator.remove();
            }
        }

        iterator = historyPunishments.iterator();
        while (iterator.hasNext()) {
            Punishment punishment = iterator.next();
            if (punishment.getPlayerUUID().equals(uuid)) {
                iterator.remove();
            }
        }
	}
	
	public boolean isCached(String UUID) {
		if(cachedData.contains(UUID)) return true;
		return false;
	}

	public void addCached(PlayerProfile profile) {
		cachedData.add(profile.getUuid());
		cachedData.add(profile.getName());
	}
	
	/* 
	 * Getters & Setters
	 * */
	
	public Set<Punishment> getLoadedPunishments(){
		return this.punishments;
	}
	
	public Set<Punishment> getHistoryPunishments(){
		return this.historyPunishments;
	}
	
	public static PunishmentManager get() {
		return instance;
	}
	
}
