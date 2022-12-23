package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionRankPermissions {
    public static void set(FactionsManager manager, UUID id, String permissions) {
        if(!manager.rankManager.hasRank(id)) return;

        manager.database.execute("UPDATE factionRanks SET permissions = ? WHERE id = ?", permissions, id.toString());
    }
}
