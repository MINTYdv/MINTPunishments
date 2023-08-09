package xyz.mintydev.punishment.core;

import java.util.Set;

import org.bukkit.Bukkit;

import xyz.mintydev.punishment.managers.PunishmentManager;

public class PlayerProfile {

	private final String uuid, name;
    private final Set<Punishment> punishments, history;

    public PlayerProfile(String uuid, String name, Set<Punishment> punishments, Set<Punishment> history) {
        this.uuid = uuid;
        this.name = name;
        this.punishments = punishments;
        this.history = history;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Set<Punishment> getPunishments() {
        return punishments;
    }

    public Set<Punishment> getHistory() {
        return history;
    }

    // The player has no ban, accept loading his data into the cache
    public void acceptLoad() {
    	PunishmentManager.get().getLoadedPunishments().addAll(punishments);
    	PunishmentManager.get().getHistoryPunishments().addAll(history);
    	PunishmentManager.get().addCached(this);
    }
    
    public Punishment getBlacklist() {
        for (final Punishment pt : punishments) {
            if (pt.getType() == PunishmentType.BLACKLIST || pt.getType().getBase() == PunishmentType.BLACKLIST && !pt.isExpired()) {
                return pt;
            }
        }
        return null;
    }
    
    public Punishment getBan() {
        for (final Punishment pt : punishments) {
            if (pt.getType() == PunishmentType.BAN || pt.getType().getBase() == PunishmentType.BAN && !pt.isExpired()) {
                return pt;
            }
        }
        return null;
    }

}
