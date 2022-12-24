package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import org.bukkit.Location;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.LocationUtil;

public class CreateHome {
    public static FactionHome create(FactionsManager manager, UUID id, String displayName, Location location, Faction faction) {
        if(manager.homeManager.hasHome(id)) return manager.homeManager.getHome(id);

        manager.database.execute("INSERT INTO factionHomes(id, displayName, location, faction) VALUES(?, ?, ?, ?)", 
            id.toString(), displayName, LocationUtil.serializeLocation(location), faction.getID().toString());
       
        FactionHome home = manager.homeManager.loadHome(id);

        return home;
    }
}
