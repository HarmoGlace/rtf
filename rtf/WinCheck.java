package fr.harmoglace.plugin.rtf;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.harmoglace.plugin.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;
import java.util.Optional;

public class WinCheck implements Listener {
    private main main;
    public WinCheck(main main) {
        this.main = main;
    }

    private Optional<ProtectedRegion> getRegion(Player player) {
        Location location = player.getLocation();
        LocalPlayer localPlayer = main.getWorldGuard().wrapPlayer(player);
        Vector playerVector = BukkitUtil.toVector(location);
        RegionContainer container = main.getWorldGuard().getRegionContainer();
        RegionManager regionManager = container.get(player.getWorld());

        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(playerVector);

        ProtectedRegion region = null;

        for (ProtectedRegion regions : applicableRegionSet) {
            if (regions.contains(playerVector)) {
                region = regions;
            }
        }
        Optional<ProtectedRegion> opt = Optional.ofNullable(region);
        return opt;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Optional<ProtectedRegion> region = getRegion(player);

        Location location = player.getLocation();
        if (region.isPresent()) {
            String name = region.get().getId();

            PermissionUser user = PermissionsEx.getUser(player);
            List<String> ugroups = user.getParentIdentifiers();

            ItemStack helmet = player.getInventory().getHelmet();

            if (helmet != null && helmet.getType() == Material.BANNER) {
                if (name.equalsIgnoreCase("rouge") && ugroups.contains("rouge")) { // Si un rouge est revenu chez lui
                    Bukkit.broadcastMessage("§4Les rouges on gagné !");
                } else if (name.equalsIgnoreCase("bleu") && ugroups.contains("bleu")) {
                    Bukkit.broadcastMessage("§9Les bleus on gagné !");
                }

                if (name.equalsIgnoreCase("rouge") && ugroups.contains("rouge") || name.equalsIgnoreCase("bleu") && ugroups.contains("bleu")) {
                    for (Player p : Bukkit.getWorld("world").getPlayers()) {
                        PermissionUser u = PermissionsEx.getUser(p);
                        for (String s : u.getParentIdentifiers()) {
                            u.removeGroup(s);
                        }

                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard board = manager.getMainScoreboard();

                        Team bleu = board.getTeam("bleu");
                        if (bleu == null) {
                            bleu = board.registerNewTeam("bleu");
                        }

                        Team rouge = board.getTeam("rouge");
                        if (rouge == null) {
                            rouge = board.registerNewTeam("rouge");
                        }


                        if (bleu.hasEntry(player.getName())) {
                            bleu.removeEntry(player.getName());
                        }

                        if (rouge.hasEntry(player.getName())) {
                            rouge.removeEntry(player.getName());
                        }

                        player.setDisplayName(player.getName());

                        p.getInventory().clear();

                        p.getInventory().clear(39);

                        p.updateInventory();

                        p.setGameMode(GameMode.SPECTATOR);

                        Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                            public void run() {
                                p.openInventory(main.inventories.inventaire());
                                p.setGameMode(GameMode.SURVIVAL);
                            }
                        }, 600);
                    }
                }


            }


        }


    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null) {
            String helmetname = player.getInventory().getHelmet().getItemMeta().getDisplayName();
            if (helmetname != null && helmetname.equalsIgnoreCase("§4Drapeau rouge") || helmetname.equalsIgnoreCase("§9Drapeau bleu")) {
                event.setCancelled(true);
            }
        }

    }
}
