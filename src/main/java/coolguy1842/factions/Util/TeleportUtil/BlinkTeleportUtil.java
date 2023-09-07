// taken and modified from https://github.com/TheMrJezza/HorseTpWithMe

package coolguy1842.factions.Util.TeleportUtil;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Util.FactionsLogger;
import static coolguy1842.factions.Util.TeleportUtil.GeneralUtil.*;

import java.util.*;


// Blink is a static implementation of burst...
// Burst was the first successful "same tick" teleport method
public class BlinkTeleportUtil {

    public static final boolean BUKKIT_REFRESH;

    static {
        boolean newAPIs = false;
        try {
            Player.class.getMethod("showEntity", Plugin.class, Entity.class);
            Player.class.getMethod("hideEntity", Plugin.class, Entity.class);
            newAPIs = true;
        } catch (NoSuchMethodException e) {
            FactionsLogger.warning("§cOutdated Bukkit API§7: §eHorseTpWithMe won't resolve graphical \"bugs\" that occur.");
            FactionsLogger.warning("§eThe plugin will try to avoid glitches on a \"best effort\" basis.");
        }
        BUKKIT_REFRESH = newAPIs;
    }

    // teleport an entity and passengers as fast as possible
    // DO NOT CALL THIS METHOD UNLESS THE DRIVER HAS BEEN EJECTED!
    public static void teleport(@NotNull final Player player, @NotNull final Location destination) {
        final Entity vehicle = player.getVehicle();

        // get the current passengers of the vehicle [COPY THE LIST]
        List<Entity> passengers = new ArrayList<>(vehicle.getPassengers());

        // eject the vehicle
        vehicle.eject();

        runTask(() -> {
            // teleport passengers and vehicle to destination
            vehicle.teleport(destination);
            vehicle.setFallDistance(-Float.MAX_VALUE);
            refreshEntity(player, vehicle);
            //EntityNMS.refreshEntity(driver, vehicle);
            for (Entity passenger : passengers) {
                passenger.teleport(destination);
                passenger.setFallDistance(-Float.MAX_VALUE);
                refreshEntity(player, passenger);
                //EntityNMS.refreshEntity(driver, passenger);
            }
        

            // run our logic at the end of the current tick
            runTask(() -> stageOne(vehicle, player, passengers), 0);
        }, BUKKIT_REFRESH ? 0 : 2L);
    }

    private static void stageOne(
            @NotNull Entity vehicle, @NotNull Player driver, @NotNull Collection<Entity> passengers
    ) {
        // At this stage, it is possible that some or all entity references are invalid
        // remember to grab fresh instances at least once per tick going forward.

        final List<Entity> validEntities = new ArrayList<>();
        final Entity vehicleValid;

        // initial validation check
        boolean anyInvalid = false;
        for(Entity e : passengers) {
            if(e.isValid()) continue;

            anyInvalid = true;
        }

        if(!vehicle.isValid() || anyInvalid) {
            Map<UUID, Entity> found = findEntities(vehicle.getWorld(), passengers.toArray(new Entity[0]));
            Map<UUID, Entity> vehicleFound = findEntities(vehicle.getWorld(), vehicle);
            vehicleValid = vehicleFound.get(vehicle.getUniqueId());

            for(Entity e : passengers) {
                if(found.containsKey(e.getUniqueId())) {
                    validEntities.add(e);
                }
            }

            if (validEntities.size() != passengers.size() || vehicleValid == null) {
                runTask(() -> stageOne(vehicle, driver, passengers), 1);
                return;
            }
        }

        runTask(() -> {
            for(Entity e : validEntities) {
                vehicle.addPassenger(e);
            }
            
            runTask(() -> stageTwo(vehicle, driver, passengers), 1);
        }, BUKKIT_REFRESH ? 0 : 2);
    }

    private static void stageTwo(
            @NotNull Entity vehicle, @NotNull Player driver, @NotNull Collection<Entity> passengers
    ) {
        ArrayList<Entity> toValidate = new ArrayList<>(passengers);
        toValidate.add(vehicle);

        Map<UUID, Entity> found = findEntities(vehicle.getWorld(), toValidate.toArray(new Entity[0]));
        if (found.size() != toValidate.size()) runTask(() -> stageTwo(vehicle, driver, passengers), 1);
        else {
            Entity vVehicle = found.get(vehicle.getUniqueId());
            for (Entity e : passengers) vVehicle.addPassenger(found.get(e.getUniqueId()));
            refreshEntity((Player) found.get(driver.getUniqueId()), vVehicle);//.sendPassengers((Player) found.get(driver.getUniqueId()), vVehicle);
        }
    }

    public static void refreshEntity(@NotNull Player player, @NotNull org.bukkit.entity.Entity entity) {
        if (BUKKIT_REFRESH) {
            // This allows the next line to function.
            player.hideEntity(Globals.plugin, entity);
            // This is the important stuff.
            player.showEntity(Globals.plugin, entity);
        } else {
            // TODO Insert ProtocolLib / NMS Solution here.
        }
    }
}