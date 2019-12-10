package fr.harmoglace.plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.harmoglace.plugin.commands.CommandTest;
import fr.harmoglace.plugin.commands.mp;
import fr.harmoglace.plugin.events.Others;
import fr.harmoglace.plugin.rtf.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class main extends JavaPlugin {


    public setTitles titles = new setTitles();
    public Inventories inventories = new Inventories();
    public Locations locations = new Locations(this);

    public List<Player> players = new ArrayList<Player>();

    public HashMap<Player, String> kits = new HashMap<Player, String>();

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }


    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("Plugin demarre");



        getCommand("annoncer").setExecutor(new CommandTest(this));
        getCommand("mp").setExecutor(new mp());
        getServer().getPluginManager().registerEvents(new ChooseTeam(this), this);
        getServer().getPluginManager().registerEvents(new WinCheck(this), this);
        getServer().getPluginManager().registerEvents(new KillPlayer(this), this);
        getServer().getPluginManager().registerEvents(new CheckFlagsBreak(this), this);
        getServer().getPluginManager().registerEvents(new Others(this), this);




    }


    @Override
    public void onDisable() {
        System.out.println("Plugin eteint");
    }

}