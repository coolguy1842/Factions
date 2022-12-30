package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteFactionAllyInvite {
    public static void delete(FactionsManager manager, Faction inviter, Faction invited) {
        if(!invited.hasAllyInvite(inviter)) return;

        manager.database.execute("DELETE FROM factionAllyInvites WHERE inviter = ? AND invited = ?", inviter.getID().toString(), invited.getID().toString());
        invited.allyInvites.remove(inviter);
    }
}
