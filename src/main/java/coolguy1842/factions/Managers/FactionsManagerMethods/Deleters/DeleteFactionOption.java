package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class DeleteFactionOption {
    public static void delete(FactionsManager manager, String option, UUID factionID) {
        if(!manager.factionManager.hasFaction(factionID)) return;

        manager.database.execute("DELETE FROM factionOptions WHERE option = ? AND faction = ?", option, factionID.toString());
    }
}
