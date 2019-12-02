package fr.harmoglace.plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.harmoglace.plugin.commands.CommandTest;
import fr.harmoglace.plugin.commands.mp;
import fr.harmoglace.plugin.events.joinEvent;
import fr.harmoglace.plugin.events.messageEvent;
import fr.harmoglace.plugin.events.quitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class main extends JavaPlugin {


    public setTitles titles = new setTitles();






    @Override
    public void onEnable() {
        System.out.println("Plugin demarre");
        getCommand("annoncer").setExecutor(new CommandTest(this));
        getCommand("mp").setExecutor(new mp());
        getServer().getPluginManager().registerEvents(new joinEvent(this), this);
        getServer().getPluginManager().registerEvents(new quitEvent(), this);
        getServer().getPluginManager().registerEvents(new messageEvent(), this);






        Timer msg = new Timer();

//        msg.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Bukkit.broadcastMessage("Yo les tracteurs, oubliez pas Harmo c'est le meilleur");
//            }
//        }, 10000000, 10000000);
    }


    @Override
    public void onDisable() {
        System.out.println("Plugin eteint");
    }



}