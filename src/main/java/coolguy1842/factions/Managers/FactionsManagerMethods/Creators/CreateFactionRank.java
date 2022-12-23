package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class CreateFactionRank {
    public static FactionRank create(FactionsManager manager, UUID id, Faction faction, String displayName, Boolean isDefault, String... permissions) {
        if(manager.rankManager.hasRank(id)) return manager.rankManager.getRank(id);

        manager.database.execute("INSERT INTO factionRanks(id, faction, displayName, isDefault, permissions) VALUES(?, ?, ?, ?, ?)", 
            id.toString(), faction.getID(), displayName, isDefault, String.join(",", permissions));
        
        return manager.rankManager.loadRank(id);
    }
}
