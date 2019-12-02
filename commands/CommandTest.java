package fr.harmoglace.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;


import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender author, Command command, String alias, String[] args) {
        if (author instanceof Player) {
            Player player = (Player)author;
            if (args.length == 0) {
                player.sendMessage("Merci d'indiquer un argument");
            } else {
                String msg = String.join(" ", args);
                Bukkit.broadcastMessage("§c[Important] " + msg);
            }

            return true;
        }

        return false;
    }
}
