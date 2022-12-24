package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionHome;

import java.util.UUID;

import org.bukkit.Location;

import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.LocationUtil;

public class SetHomeLocation {
    public static void set(FactionsManager manager, UUID id, Location location) {
        if(!manager.homeManager.hasHome(id)) return;

        manager.database.execute("UPDATE factionHomes SET location = ? WHERE id = ?", LocationUtil.serializeLocation(location), id.toString());
    }
}
