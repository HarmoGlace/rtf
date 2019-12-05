package fr.harmoglace.plugin.rtf;

import fr.harmoglace.plugin.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

public class ChooseTeam implements Listener {
    private main main;

    public ChooseTeam(main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        System.out.println("Join");
        final Player player = event.getPlayer();
        event.setJoinMessage("");
        player.sendMessage("Bienvenue sur le serveur !");

        PermissionUser user = PermissionsEx.getUser(player);
        user.removeGroup("bleu");
        user.removeGroup("rouge");

        player.teleport(main.locations.wait);
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);


        Bukkit.getScheduler().runTaskLater((main), new Runnable() {
            public void run() {
                PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, 99999, 1);

                player.addPotionEffect(saturation);

                player.openInventory(main.inventories.inventaire());
            }
        }, 5);

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory clicked = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (current != null) {

            ItemMeta m = current.getItemMeta();
            if (m != null) {
                String name = current.getItemMeta().getDisplayName();
                if (name != null) {
                    if (clicked.getName().equals(main.inventories.inventaire().getName())) {
                        event.setCancelled(true);
                        if (current != null && current.getItemMeta() != null) {

                            ScoreboardManager manager = Bukkit.getScoreboardManager();
                            Scoreboard board = manager.getMainScoreboard();

                            if (current.getItemMeta().getDisplayName() == "§9Equipe bleue") {


                                PermissionUser user = PermissionsEx.getUser(player);
                                user.addGroup("bleu");
                                player.teleport(main.locations.bleuspawn);


                                Team bleu = board.getTeam("bleu");
                                if (bleu == null) {
                                    bleu = board.registerNewTeam("bleu");
                                }


                                bleu.setPrefix("§9Bleu ");

                                bleu.setAllowFriendlyFire(false);

                                bleu.setNameTagVisibility(NameTagVisibility.ALWAYS);

                                bleu.addEntry(player.getName());

                                player.setScoreboard(board);


                                player.setDisplayName("§9Bleu " + player.getDisplayName());


                            } else if (current.getItemMeta().getDisplayName() == "§4Equipe rouge") {


                                PermissionUser user = PermissionsEx.getUser(player);
                                user.addGroup("rouge");
                                player.teleport(main.locations.rougespawn);


                                Team rouge = board.getTeam("rouge");
                                if (rouge == null) {
                                    rouge = board.registerNewTeam("rouge");
                                }


                                rouge.setPrefix("§4Rouge ");

                                rouge.setAllowFriendlyFire(false);

                                rouge.setNameTagVisibility(NameTagVisibility.ALWAYS);

                                rouge.addEntry(player.getName());

                                player.setScoreboard(board);

                                player.setDisplayName("§4Rouge " + player.getDisplayName());
                            }
                        }

                    }


                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Inventory closed = event.getInventory();
        if (closed.getName().equals(main.inventories.inventaire().getName())) {
            PermissionUser user = PermissionsEx.getUser(player);
            List<String> ugroups = user.getParentIdentifiers();


            if (!ugroups.contains("rouge") && !ugroups.contains("bleu")) {

                Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                    public void run() {
                        player.openInventory(closed);
                    }
                }, 5);

            } else if (!main.kits.containsKey(player)) {
                if (closed.getName().equalsIgnoreCase(main.inventories.kitChoose().getName())) {
                    Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                        public void run() {
                            player.openInventory(closed);
                        }
                    }, 5);
                } else {
                    Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                        public void run() {
                            player.openInventory(main.inventories.kitChoose());
                        }
                    }, 5);
                }


            }

        }

    }
}
