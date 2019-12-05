package fr.harmoglace.plugin.rtf;

import fr.harmoglace.plugin.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

public class KillPlayer implements Listener {
    private main main;
    public KillPlayer(main main) {
        this.main = main;
    }



    public void killPlayer(Player player) {
        main.titles.sendTitle(player, 3, "§4Tu es mort", "");
        main.kits.remove(player);
        player.getInventory().clear();



        player.setHealth(20);
        player.setFoodLevel(20);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType() != PotionEffectType.SATURATION) {
                player.removePotionEffect(effect.getType());
            }

        }

        PermissionUser user = PermissionsEx.getUser(player);
        List<String> ugroups = user.getParentIdentifiers();

        main.players.add(player);

        if (ugroups.contains("rouge")) {
            player.teleport(main.locations.rougespawn);
        } else if (ugroups.contains("bleu")) {
            player.teleport(main.locations.bleuspawn);
        } else {
            player.teleport(main.locations.wait);
        }

        player.openInventory(main.inventories.kitChoose());

        Bukkit.getScheduler().runTaskLater((main), new Runnable() {
            public void run() {
                main.players.remove(player);
            }
        }, 10);
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (main.players.contains(player)) {
                event.setCancelled(true);
            } else if (event.getFinalDamage() >= player.getHealth()) {
                event.setCancelled(true);

                killPlayer(player);


            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (location.getY() <= 9 && player.getGameMode() == GameMode.SURVIVAL) {

            killPlayer(player);

        }

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
                    if (clicked.getName().equals(main.inventories.kitChoose().getName())) {
                        event.setCancelled(true);


                        if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§rKit guerrier")) {
                            main.kits.put(player, "guerrier");
                            main.inventories.setKit(player, "guerrier");
                            player.closeInventory();
                        } else if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§rKit archer")) {
                            main.kits.put(player, "archer");
                            main.inventories.setKit(player, "archer");
                            player.closeInventory();
                        }
                    } else if (clicked.getName().equals(player.getInventory().getName())) {
                        if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§4Drapeau rouge") || current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Drapeau bleu")) {
                            event.setCancelled(true);
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
        if (closed.getName().equals(main.inventories.kitChoose().getName())) {
            if (!main.kits.containsKey(player)) {
                Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                    public void run() {
                        player.openInventory(closed);
                    }
                }, 5);
            }
        }

    }
}
