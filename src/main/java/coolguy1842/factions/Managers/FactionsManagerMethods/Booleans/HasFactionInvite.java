package coolguy1842.factions.Managers.FactionsManagerMethods.Booleans;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class HasFactionInvite {
    public static Boolean has(FactionsManager manager, UUID id, UUID factionID) {
        if(manager.inviteManager.invites.containsKey(id)) {
            if(manager.inviteManager.invites.get(id).contains(factionID)) {
                return true;
            }
        }

        return false;
    }
}
