package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import org.bukkit.Location;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.LocationUtil;

public class LoadHomes {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT id FROM factionHomes");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadHome(manager, UUID.fromString(rows.getString("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static FactionHome loadHome(FactionsManager manager, UUID id) {
        CachedRowSet rows = manager.database.query("SELECT * FROM factionHomes WHERE id = ?", id.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try {
            rows.next();

            String displayName = rows.getString("displayName");

            Faction faction = manager.factionManager.getFaction(UUID.fromString(rows.getString("faction")));
            Location location = LocationUtil.deserializeLocation(rows.getString("location"));

            FactionHome home = new FactionHome(id, displayName, faction, location);
            
            manager.homeManager.homes.put(id, home);

            faction.homes.put(id, home);
            faction.homesByName.put(displayName, home);

            return home;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
