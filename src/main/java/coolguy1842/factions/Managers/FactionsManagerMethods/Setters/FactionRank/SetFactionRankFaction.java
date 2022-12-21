package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionRankFaction {
    public static void set(FactionsManager manager, UUID id, UUID factionID) {
        if(!manager.rankManager.hasRank(id)) return;

        manager.database.execute("UPDATE ranks SET faction = ? WHERE id = ?", factionID.toString(), id.toString());
    }
}
