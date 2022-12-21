package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionMoney {
    public static void set(FactionsManager manager, UUID id, Long money) {
        if(!manager.factionManager.hasFaction(id)) return;

        manager.database.execute("UPDATE factions SET money = ? WHERE id = ?", money, id.toString());
    }
}
