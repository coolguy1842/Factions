package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import java.util.UUID;

import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class GetFactionRank {
    public GetFactionRank() {}

    public static FactionRank get(FactionsManager manager, UUID id) {
        if(manager.rankManager.hasRank(id)) return manager.rankManager.ranks.get(id);
        return null;
    }
}
