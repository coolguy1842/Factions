package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionHome;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetHomeDisplayName {
    public static void set(FactionsManager manager, UUID id, String displayName) {
        if(!manager.homeManager.hasHome(id)) return;

        manager.database.execute("UPDATE factionHomes SET displayName = ? WHERE id = ?", displayName, id.toString());
    }
}
