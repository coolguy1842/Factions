package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteFactionAlly {
    public static void delete(FactionsManager manager, Faction faction1, Faction faction2) {
        if(!faction1.isAllied(faction2)) return;

        manager.database.execute("DELETE FROM factionAllies WHERE faction1 = ? AND faction2 = ?", faction1.getID().toString(), faction2.getID().toString());
        manager.database.execute("DELETE FROM factionAllies WHERE faction2 = ? AND faction1 = ?", faction1.getID().toString(), faction2.getID().toString());
        faction1.allies.remove(faction2);
        faction2.allies.remove(faction1);
    }
}
