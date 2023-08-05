package xyz.mintydev.punishment.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
    	final Player player = e.getPlayer();
    	
    	if(!PunishmentManager.get().isCached(player.getUniqueId().toString())) return;
    	
    	PunishmentManager.get().removeCache(player.getName(), player.getUniqueId());
    }
	
}
