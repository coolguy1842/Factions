package coolguy1842.factions;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public final class Globals {
    public static JavaPlugin plugin;

    // key = requested
    // value = requester
    public static HashMap<Player, Player> tpaRequests;
    
    // key = requester
    // value = requested
    public static HashMap<Player, Player> tpaHereRequests;

    public static Component factionsPrefix = Component.text("[").color(TextColor.color(200, 200, 200))
                                            .append(Component.text("Factions").color(TextColor.color(0, 183, 255)))
                                            .append(Component.text("] "));
}
