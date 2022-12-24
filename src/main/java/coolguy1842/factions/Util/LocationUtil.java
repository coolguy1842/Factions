package coolguy1842.factions.Util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    public static String serializeLocation(Location location) {
        String out;

        out = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch() + ";" + location.getWorld().getUID().toString();
        
        return out;
    }
    
    public static Location deserializeLocation(String locationStr) {
        String[] splitStr = locationStr.split(";");

        double x = Double.parseDouble(splitStr[0]);
        double y = Double.parseDouble(splitStr[1]);
        double z = Double.parseDouble(splitStr[2]);

        float yaw = Float.parseFloat(splitStr[3]);
        float pitch = Float.parseFloat(splitStr[4]);

        World world = Bukkit.getWorld(UUID.fromString(splitStr[5]));

        Location location = new Location(world, x, y, z, yaw, pitch);

        return location;
    }
}
