package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class CreateFactionInvite {
    public static void create(FactionsManager manager, UUID playerID, UUID faction) {
        if(manager.inviteManager.playerHasInvite(playerID, faction)) return;
        
        manager.database.execute("INSERT INTO factionInvites(invited, faction) VALUES(?, ?)", playerID.toString(), faction.toString());
        manager.inviteManager.loadInvite(playerID, faction);
    }

}
