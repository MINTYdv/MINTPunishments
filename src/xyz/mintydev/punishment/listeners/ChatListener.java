package xyz.mintydev.punishment.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import xyz.mintydev.punishment.MINTPunishment;
import xyz.mintydev.punishment.managers.PunishmentManager;

public class ChatListener implements Listener {

	private final MINTPunishment main;
	
	public ChatListener(MINTPunishment main) {
		this.main = main;
	}
	
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
    	
    	boolean result = PunishmentManager.get().tryChat(event.getPlayer());
    	
        if (!result) {
            event.setCancelled(true);
        }
    }

}
