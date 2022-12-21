package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class GetFaction {
    public static Faction get(FactionsManager manager, UUID id) {
        if(manager.factionManager.hasFaction(id)) return manager.factionManager.factions.get(id);
        
        return null;
    }

    public static Faction get(FactionsManager manager, String displayName) {
        if(manager.factionManager.factionsNameLookup.containsKey(displayName)) {
            return manager.factionManager.factions.get(manager.factionManager.factionsNameLookup.get(displayName));
        }
        
        return null;
    }
}
