package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class CreateFactionAllyInvite {
    public static void create(FactionsManager manager, Faction inviter, Faction invited) {
        if(invited.hasAllyInvite(inviter)) return;
        
        manager.database.execute("INSERT INTO factionAllyInvites(inviter, invited) VALUES(?, ?)", inviter.getID().toString(), invited.getID().toString());
        invited.allyInvites.add(inviter);
    }

}
