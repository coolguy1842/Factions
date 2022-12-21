package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionOptions {
    public static void set(FactionsManager manager, UUID id, String options) {
        if(!manager.factionManager.hasFaction(id)) return;

        manager.database.execute("UPDATE factions SET options = ? WHERE id = ?", options, id.toString());
    }
}
