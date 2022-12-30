package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class MakeFactionsAllies {
    public static void set(FactionsManager manager, Faction faction1, Faction faction2) {
        if(faction1.isAllied(faction2)) return;

        manager.database.execute("INSERT INTO factionAllies(faction1, faction2) VALUES(?, ?)", faction1.getID().toString(), faction2.getID().toString());
        faction1.allies.add(faction2);
        faction2.allies.add(faction1);
    }
}
