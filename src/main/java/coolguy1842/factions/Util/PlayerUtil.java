package coolguy1842.factions.Util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import net.kyori.adventure.text.Component;

public class PlayerUtil {
    public static void teleportPlayer(Player p, Location location) {
        if(!p.isInsideVehicle()) {
            p.setFallDistance(0);
            p.teleport(location, TeleportCause.PLUGIN);
            
            return;
        }

        Entity vehicle = p.getVehicle();
        vehicle.setFallDistance(0);
        vehicle.teleport(location, TeleportCause.PLUGIN, true, false);
    }


    public static UUID getPlayerUUID(String exactName) {
        Player p = Bukkit.getPlayerExact(exactName);
        
        if(p != null) return p.getUniqueId();
        
        OfflinePlayer oP = Bukkit.getOfflinePlayerIfCached(exactName);
        if(oP != null) return oP.getUniqueId();
    
        return null;
    } 
    

    public static String getPlayerUsername(UUID id) {
        Player p = Bukkit.getPlayer(id);
        
        if(p != null) return p.getName();
        
        OfflinePlayer oP = Bukkit.getOfflinePlayer(id);
        if(oP != null) return oP.getName();
    
        return null;
    }


    public static Component getPlayerDisplayName(String exactName) {
        Player p = Bukkit.getPlayerExact(exactName);
        
        if(p != null) return p.displayName();
        
        OfflinePlayer oP = Bukkit.getOfflinePlayerIfCached(exactName);
        if(oP != null) return Component.text(oP.getName());
    
        return null;
    } 
    
    public static Component getPlayerDisplayName(UUID id) {
        Player p = Bukkit.getPlayer(id);
        
        if(p != null) return p.displayName();
        
        OfflinePlayer oP = Bukkit.getOfflinePlayer(id);
        if(oP != null) return Component.text(oP.getName());
    
        return null;
    } 
}
