package coolguy1842.factions;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Globals {
    public static JavaPlugin plugin;

    // key = requested
    // value = requester
    public static HashMap<Player, Player> tpaRequests;
    
    // key = requester
    // value = requested
    public static HashMap<Player, Player> tpaHereRequests;
}
