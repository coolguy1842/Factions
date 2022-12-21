package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionDisplayName {
    public static void set(FactionsManager manager, UUID id, String displayName) {
        if(!manager.factionManager.hasFaction(id)) return;

        manager.database.execute("UPDATE factions SET displayName = ? WHERE id = ?", displayName, id.toString());
    }
}
