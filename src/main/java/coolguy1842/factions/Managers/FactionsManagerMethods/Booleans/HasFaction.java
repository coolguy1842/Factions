package coolguy1842.factions.Managers.FactionsManagerMethods.Booleans;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class HasFaction {
    public static Boolean has(FactionsManager manager, UUID id) {
        return manager.factionManager.factions.containsKey(id);
    }
    
    public static Boolean has(FactionsManager manager, String displayName) {
        return manager.factionManager.factionsNameLookup.containsKey(displayName);
    }
}
