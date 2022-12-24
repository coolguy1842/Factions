package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteHome {
    public static void delete(FactionsManager manager, UUID id) {
        if(!manager.homeManager.hasHome(id)) return;
        FactionHome home = manager.homeManager.getHome(id);
        
        manager.database.execute("DELETE FROM factionHomes WHERE id = ?", id.toString());
        

        home.getFaction().homes.remove(id);
        home.getFaction().homesByName.remove(home.getDisplayName());

        manager.homeManager.homes.remove(id);
    }

    public static void delete(FactionsManager manager, String displayName, Faction faction) {
        if(!faction.hasHome(displayName)) return;
        FactionHome home = manager.homeManager.getHome(displayName, faction);

        delete(manager, home.getID());
    }
}
