package fr.harmoglace.plugin.rtf;

import fr.harmoglace.plugin.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Locations {
    private main main;

    public Locations(main main) {
        this.main = main;
    }

    public World world = Bukkit.getWorld("world");
    public Location bleuspawn = new Location(world, 2533.651, 10, -299.508, 90.6f, -1.5f);
    public Location rougespawn = new Location(world, 2489.744, 10, -297.373, -91.1f, 1.1f);
    public Location wait = new Location(world, 2505.308, 155, -310.932, 89.8f, 10f);


}
