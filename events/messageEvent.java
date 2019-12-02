package fr.harmoglace.plugin.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.awt.*;

public class messageEvent implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
//        event.setCancelled(true);
//
//        Player player = event.getPlayer();
//
//        PermissionUser user = PermissionsEx.getUser(player);
//        String teamname = ChatColor.translateAlternateColorCodes('&', user.getPrefix()) + user.getName();
//        if (teamname != player.getPlayerListName()) {
//            player.setPlayerListName(teamname);
//        }
//
//
//        Bukkit.broadcastMessage(player.getPlayerListName() + "§r : " + event.getMessage());
        event.setFormat("%1$s §r: %2$s");


    }

}
