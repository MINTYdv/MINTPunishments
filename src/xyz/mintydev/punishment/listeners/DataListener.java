package xyz.mintydev.punishment.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.core.Punishment;
import xyz.mintydev.punishment.managers.LangManager;
import xyz.mintydev.punishment.managers.PunishmentManager;

public class DataListener implements Listener {

	private final MINTPunishment main;
	
	public DataListener(MINTPunishment main) {
		this.main = main;
	}
	
    @EventHandler(priority = EventPriority.HIGH)
    public void onConnect(AsyncPlayerPreLoginEvent event) {
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED){
        	
        	boolean connectionResult = PunishmentManager.get().tryConnection(event.getUniqueId());
        	if(!connectionResult) {
        		event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, LangManager.getMessageListSplitted("layouts.banned").toString());
        	}
        }
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		// Load player profile
		final Player player = e.getPlayer();
		main.getProfileManager().getProfile(player.getUniqueId());
		
		List<Punishment> punishments = PunishmentManager.get().getPunishments(player.getUniqueId(), null, true);
		for(Punishment p : punishments) {
			System.out.println(p.getPlayerName());
			System.out.println(p.getId() + " - " + p.getType().toString());
		}
	}
	
}
