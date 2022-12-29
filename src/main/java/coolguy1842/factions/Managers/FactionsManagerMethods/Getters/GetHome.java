package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Managers.FactionsManager;

public class GetHome {
    public GetHome() {}

    public static FactionHome get(FactionsManager manager, UUID id) {
        if(manager.homeManager.hasHome(id)) return manager.homeManager.homes.get(id);
        return null;
    }

    public static FactionHome get(FactionsManager manager, String displayName, Faction faction) {
        if(faction.hasHome(displayName)) return faction.getHome(displayName);
        return null;
    }
}
