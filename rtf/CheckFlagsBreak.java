package fr.harmoglace.plugin.rtf;

import fr.harmoglace.plugin.main;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

public class CheckFlagsBreak implements Listener {

    private main main;

    public CheckFlagsBreak(main main) {
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (block.getType() == Material.OBSIDIAN) {
            if (player.getInventory().getItemInHand().getType() != Material.BEDROCK) {
                event.setCancelled(true);
            }

        } else if (block.getType() == Material.STANDING_BANNER) {
            Banner banniere = (Banner) block.getState();

            PermissionUser user = PermissionsEx.getUser(player);
            List<String> ugroups = user.getParentIdentifiers();

            if (banniere.getBaseColor() == DyeColor.BLUE) {
                if (ugroups.contains("rouge")) {
                    block.setType(Material.AIR);

                    ItemStack ib = new ItemStack(Material.BANNER, 1, (byte) 4);

                    ItemMeta ib2 = ib.getItemMeta();

                    ib2.setDisplayName("§9Drapeau bleu");
                    ib.setItemMeta(ib2);

                    PlayerInventory inventoryp = player.getInventory();

                    inventoryp.clear();

                    for (int i = 0; i <= 35; i++) {
                        inventoryp.setItem(i, ib);
                    }


                    inventoryp.setHelmet(ib);
                    Bukkit.broadcastMessage("§r" + player.getDisplayName() + "§9 a volé le drapeau des bleus !");
                }
            } else if (banniere.getBaseColor() == DyeColor.RED) {
                if (ugroups.contains("bleu")) {
                    block.setType(Material.AIR);

                    ItemStack ib = new ItemStack(Material.BANNER, 1, (byte) 1);

                    ItemMeta ib2 = ib.getItemMeta();

                    ib2.setDisplayName("§4Drapeau rouge");
                    ib.setItemMeta(ib2);

                    PlayerInventory inventoryp = player.getInventory();

                    inventoryp.clear();

                    for (int i = 0; i <= 35; i++) {
                        inventoryp.setItem(i, ib);
                    }


                    inventoryp.setHelmet(ib);
                    Bukkit.broadcastMessage("§r" + player.getDisplayName() + "§4 a volé le drapeau des rouges !");
                }
            }
            event.setCancelled(true);
        }
    }
}
