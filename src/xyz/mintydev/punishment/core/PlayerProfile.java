package xyz.mintydev.punishment.core;

import java.util.Set;

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

	public Punishment getPunishment(PunishmentType type) {
		for (final Punishment pt : punishments) {
			if (pt.getType() == type || pt.getType().getBase() == type && !pt.isExpired()) {
				return pt;
			}
		}
		return null;
	}

}
