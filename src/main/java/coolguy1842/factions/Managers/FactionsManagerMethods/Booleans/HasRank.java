package coolguy1842.factions.Managers.FactionsManagerMethods.Booleans;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class HasRank {
    public static Boolean has(FactionsManager manager, UUID id) {
        return manager.rankManager.ranks.containsKey(id);
    }
}
