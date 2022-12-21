package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class DeleteFactionInvite {
    public static void delete(FactionsManager manager, UUID id, UUID factionID) {
        if(!manager.inviteManager.playerHasInvite(id, factionID)) return;

        manager.database.execute("DELETE FROM factionInvites WHERE invited = ? AND faction = ?", id.toString(), factionID.toString());
        manager.inviteManager.invites.get(id).remove(factionID);
    }

    public static void deleteAllPlayerInvites(FactionsManager manager, UUID id) {
        if(!manager.inviteManager.invites.containsKey(id)) return;
        
        manager.inviteManager.invites.remove(id);
        manager.database.execute("DELETE FROM factionInvites WHERE invited = ?", id.toString());
    }
}
