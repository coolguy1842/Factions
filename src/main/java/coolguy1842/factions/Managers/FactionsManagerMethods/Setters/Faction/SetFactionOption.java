package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionOption {
    public static void set(FactionsManager manager, String value, String option, UUID factionID) {
        if(!manager.factionManager.hasFaction(factionID)) return;


        manager.database.execute("REPLACE INTO factionOptions(faction, option, value) values(?, ?, ?)", factionID.toString(), option, value);
    }
}
