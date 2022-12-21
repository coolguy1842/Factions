package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionLeader {
    public static void set(FactionsManager manager, UUID id, UUID leader) {
        if(!manager.factionManager.hasFaction(id)) return;

        manager.database.execute("UPDATE factions SET leader = ? WHERE id = ?", leader.toString(), id.toString());
    }
}
