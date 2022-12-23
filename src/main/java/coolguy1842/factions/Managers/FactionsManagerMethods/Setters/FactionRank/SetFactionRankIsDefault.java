package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionRankIsDefault {
    public static void set(FactionsManager manager, UUID id, Boolean isDefault) {
        if(!manager.rankManager.hasRank(id)) return;

        manager.database.execute("UPDATE factionRanks SET isDefault = ? WHERE id = ?", isDefault, id.toString());
    }
}
