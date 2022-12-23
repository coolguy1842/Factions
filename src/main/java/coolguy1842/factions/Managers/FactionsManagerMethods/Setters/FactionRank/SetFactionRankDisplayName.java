package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionRankDisplayName {
    public static void set(FactionsManager manager, UUID id, String displayName) {
        if(!manager.rankManager.hasRank(id)) return;

        manager.database.execute("UPDATE factionRanks SET displayName = ? WHERE id = ?", displayName, id.toString());
    }
}
