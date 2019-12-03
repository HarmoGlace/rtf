package fr.harmoglace.plugin.events;


//import com.mewin.WGRegionEvents.events.RegionEnterEvent;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.harmoglace.plugin.main;
import org.bukkit.*;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class joinEvent implements Listener {


    public joinEvent(main main) {
        this.main = main;
    }

    private main main;

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = main.getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }

    private Optional<ProtectedRegion> getRegion(Player player) {
        Location location = player.getLocation();
        LocalPlayer localPlayer = getWorldGuard().wrapPlayer(player);
        Vector playerVector = BukkitUtil.toVector(location);
        RegionContainer container = getWorldGuard().getRegionContainer();
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


    public Inventory inventaire() {
        Inventory inv = Bukkit.createInventory(null, 27, "§2Choisis ton équipe");

        ItemStack bleu = new ItemStack(Material.WOOL, 1, (byte) 11);

        ItemStack rouge = new ItemStack(Material.WOOL, 1, (byte) 14);

        ItemMeta bleum = bleu.getItemMeta();
        ItemMeta rougem = rouge.getItemMeta();

        bleum.setDisplayName("§9Equipe bleue");
        bleu.setItemMeta(bleum);

        rougem.setDisplayName("§4Equipe rouge");
        rouge.setItemMeta(rougem);


        inv.setItem(11, bleu);
        inv.setItem(15, rouge);
        return inv;
    }


    public Inventory kitChoose() {
        Inventory inv = Bukkit.createInventory(null, 27, "§rChoisis ton kit");

        ItemStack guerrier = new ItemStack(Material.STONE_SWORD, 1);
        ItemStack archer = new ItemStack(Material.BOW, 1);

        ItemMeta guerrierm = guerrier.getItemMeta();
        ItemMeta archerm = archer.getItemMeta();

        guerrierm.setDisplayName("§rKit Guerrier");
        guerrierm.addEnchant(Enchantment.KNOCKBACK, 1, true);
        guerrierm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        guerrierm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        guerrier.setItemMeta(guerrierm);

        archerm.setDisplayName("§rKit Archer");
        archerm.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
        archerm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        archer.setItemMeta(archerm);

        inv.setItem(11, guerrier);
        inv.setItem(15, archer);

        return inv;
    }

    public void setKit(Player player, String kit) {
        Inventory inventory = player.getInventory();
        inventory.clear();


        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

        inventory.setItem(39, helmet);
        inventory.setItem(38, chestplate);
        inventory.setItem(37, leggings);
        inventory.setItem(36, boots);

        if (kit.equalsIgnoreCase("guerrier")) {
            ItemStack sword = new ItemStack(Material.IRON_SWORD);

            ItemMeta swordm = sword.getItemMeta();

            swordm.spigot().setUnbreakable(true);

            sword.setItemMeta(swordm);

            inventory.setItem(0, sword);

        } else if (kit.equalsIgnoreCase("archer")) {
            ItemStack bow = new ItemStack(Material.BOW);
            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemStack sword = new ItemStack(Material.STONE_SWORD);

            ItemMeta bowm = bow.getItemMeta();
            ItemMeta swordm = sword.getItemMeta();

            swordm.spigot().setUnbreakable(true);

            sword.setItemMeta(swordm);

            bowm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

            bowm.spigot().setUnbreakable(true);

            bowm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            bowm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            bowm.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            bow.setItemMeta(bowm);


            inventory.setItem(0, bow);
            inventory.setItem(1, sword);
            inventory.setItem(17, arrow);
        }
    }

    World world = Bukkit.getWorld("world");
    Location bleuspawn = new Location(world, 2533.651, 10, -299.508, 90.6f, -1.5f);
    Location rougespawn = new Location(world, 2489.744, 10, -297.373, -91.1f, 1.1f);
    Location wait = new Location(world, 2505.308, 155, -310.932, 89.8f, 10f);

    List<Player> players = new ArrayList<Player>();

    HashMap<Player, String> kits = new HashMap<Player, String>();

    public void killPlayer(Player player) {
        main.titles.sendTitle(player, 3, "§4Tu es mort", "");
        kits.remove(player);
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

        players.add(player);

        if (ugroups.contains("rouge")) {
            player.teleport(rougespawn);
        } else if (ugroups.contains("bleu")) {
            player.teleport(bleuspawn);
        } else {
            player.teleport(wait);
        }

        player.openInventory(kitChoose());

        Bukkit.getScheduler().runTaskLater((main), new Runnable() {
            public void run() {
                players.remove(player);
            }
        }, 10);
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

        player.teleport(wait);
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);


        Bukkit.getScheduler().runTaskLater((main), new Runnable() {
            public void run() {
                PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, 99999, 1);

                player.addPotionEffect(saturation);

                player.openInventory(inventaire());
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
                    if (clicked.getName().equals(inventaire().getName())) {
                        event.setCancelled(true);
                        if (current != null && current.getItemMeta() != null) {

                            ScoreboardManager manager = Bukkit.getScoreboardManager();
                            Scoreboard board = manager.getMainScoreboard();

                            if (current.getItemMeta().getDisplayName() == "§9Equipe bleue") {


                                PermissionUser user = PermissionsEx.getUser(player);
                                user.addGroup("bleu");
                                player.teleport(bleuspawn);


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
                                player.teleport(rougespawn);


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

                    } else if (clicked.getName().equals(kitChoose().getName())) {
                        event.setCancelled(true);


                        if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§rKit guerrier")) {
                            kits.put(player, "guerrier");
                            setKit(player, "guerrier");
                            player.closeInventory();
                        } else if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§rKit archer")) {
                            kits.put(player, "archer");
                            setKit(player, "archer");
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
        if (closed.getName().equals(inventaire().getName())) {
            PermissionUser user = PermissionsEx.getUser(player);
            List<String> ugroups = user.getParentIdentifiers();


            if (!ugroups.contains("rouge") && !ugroups.contains("bleu")) {

                Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                    public void run() {
                        player.openInventory(closed);
                    }
                }, 5);

            } else { // Si la personne a choisit une team
                Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                    public void run() {
                        player.openInventory(kitChoose());
                    }
                }, 5);
            }


        } else if (closed.getName().equals(kitChoose().getName())) {
            if (!kits.containsKey(player)) {
                Bukkit.getScheduler().runTaskLater((main), new Runnable() {
                    public void run() {
                        player.openInventory(closed);
                    }
                }, 5);
            }
        }

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


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (players.contains(player)) {
                event.setCancelled(true);
            } else if (event.getFinalDamage() >= player.getHealth()) {
                event.setCancelled(true);

                killPlayer(player);


            }
        }
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null) {
            String helmetname = player.getInventory().getHelmet().getItemMeta().getDisplayName();
            if (helmetname.equalsIgnoreCase("§4Drapeau rouge") || helmetname.equalsIgnoreCase("§9Drapeau bleu")) {
                event.setCancelled(true);
            }
        }

    }


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Optional<ProtectedRegion> region = getRegion(player);

        Location location = player.getLocation();
        if (location.getY() <= 9 && player.getGameMode() == GameMode.SURVIVAL) {

            killPlayer(player);

        } else if (region.isPresent()) {
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
                                p.openInventory(inventaire());
                                p.setGameMode(GameMode.SURVIVAL);
                            }
                        }, 600);
                    }
                }


            }


        }


    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }
}