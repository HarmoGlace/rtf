package fr.harmoglace.plugin.commands;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender author, Command command, String alias, String[] args) {

        if (author instanceof Player) {
            Player player = (Player) author;
            if (args.length == 0) {
                player.sendMessage("§cIl faut préciser un joueur !");
            } else {
                String targetname = args[0];
                if (Bukkit.getPlayer(targetname) != null) {
                    Player target = Bukkit.getPlayer(targetname);

                    if (target.getUniqueId() == player.getUniqueId()) {
                        player.sendMessage("§cTu ne peux pas t'envoyer de messages à toi-même !");
                    } else {
                        if (args.length == 1) {
                            player.sendMessage("§cMerci de préciser un message");
                        } else {
                            String[] tomessage = (String[]) ArrayUtils.remove(args, 0);

                            String message = String.join(" ", tomessage);

                            player.sendMessage("Toi à " + target.getPlayerListName() + "§r : " + message);
                            target.sendMessage("" + player.getPlayerListName() + "§r à toi" + " : " + message);

                        }
                    }


                } else {
                    player.sendMessage("§cJoueur invalide !");
                }
            }


        }

        return false;
    }
}
