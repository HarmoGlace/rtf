package fr.harmoglace.plugin.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class quitEvent implements Listener {
    @EventHandler
    public void onLeft(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }
}
