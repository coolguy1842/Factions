package coolguy1842.factions;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
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

    public static ArrayList<String> rankPermissions = new ArrayList<>();
    static {
        rankPermissions.add("invite");
        rankPermissions.add("kick");
        
        rankPermissions.add("rename");
        
        rankPermissions.add("claim");
        rankPermissions.add("unclaim");

        rankPermissions.add("openvault");
        rankPermissions.add("createvault");
        rankPermissions.add("renamevault");
        rankPermissions.add("removevault");
        
        rankPermissions.add("sethome");
        rankPermissions.add("delhome");
        
        rankPermissions.add("withdraw");
        rankPermissions.add("deposit");
        
        rankPermissions.add("setcolor");
        
        rankPermissions.add("all");
    }

    public static HashMap<Material, Long> itemPrices = new HashMap<>();
    static {
        itemPrices.put(Material.IRON_BLOCK, 46L);
        itemPrices.put(Material.IRON_INGOT, 5L);

        itemPrices.put(Material.GOLD_BLOCK, 82L);
        itemPrices.put(Material.GOLD_INGOT, 9L);
        itemPrices.put(Material.GOLD_NUGGET, 1L);

        itemPrices.put(Material.EMERALD_BLOCK, 136L);
        itemPrices.put(Material.EMERALD, 15L);

        itemPrices.put(Material.DIAMOND_BLOCK, 271L);
        itemPrices.put(Material.DIAMOND, 30L);
    }

    Long claimPrice = 10L;

    public static ArrayList<String> factionOptions = new ArrayList<>();
    static {    
        factionOptions.add("alliesCanInteractBlock");
        factionOptions.add("alliesCanInteractEntity");
        factionOptions.add("alliesCanAttack");

        factionOptions.add("color");

        factionOptions.add("playersCanInteractBlock");
        factionOptions.add("playersCanInteractEntity");
        factionOptions.add("playersCanAttack");
    }
}
