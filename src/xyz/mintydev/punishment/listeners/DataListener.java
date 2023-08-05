package xyz.mintydev.punishment.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.managers.PunishmentManager;

public class DataListener implements Listener {

	private final MINTPunishment main;
	
	public DataListener(MINTPunishment main) {
		this.main = main;
	}
	
    @EventHandler(priority = EventPriority.HIGH)
    public void onConnect(AsyncPlayerPreLoginEvent event) {
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED){
        	
        	final String connectionResult = PunishmentManager.get().tryConnection(event.getName(), event.getUniqueId());
        	if(connectionResult != null) {
        		event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, connectionResult);
        		return;
        	}
        }
    }
	
}
