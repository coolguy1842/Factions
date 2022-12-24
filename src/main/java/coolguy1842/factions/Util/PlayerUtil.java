package coolguy1842.factions.Util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

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
}
