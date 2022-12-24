package coolguy1842.factions.Managers.FactionsManagerMethods.Booleans;

import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class HasHome {
    public static Boolean has(FactionsManager manager, UUID id) {
        return manager.homeManager.homes.containsKey(id);
    }

    public static Boolean has(FactionsManager manager, String displayName, Faction faction) {
        return faction.hasHome(displayName);
    }
}
