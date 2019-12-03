package fr.harmoglace.plugin.commands;

import fr.harmoglace.plugin.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;


import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommandTest implements CommandExecutor {

    private final main main;

    public CommandTest(main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender author, Command command, String alias, String[] args) {
        if (author instanceof Player) {
            Player player = (Player) author;
            if (args.length == 0) {
                player.sendMessage("Merci d'indiquer un argument");
            } else {

                ArrayList al = new ArrayList(Arrays.asList(args));

                al.remove(0);

                String[] al2 = (String[]) al.toArray(new String[al.size()]);


                String msg2 = String.join(" ", al2);

                Bukkit.broadcastMessage(msg2);
                String msg = String.join(" ", args);
                Bukkit.broadcastMessage("§c[Important] " + msg);
            }

            return true;
        }

        return false;
    }
}
