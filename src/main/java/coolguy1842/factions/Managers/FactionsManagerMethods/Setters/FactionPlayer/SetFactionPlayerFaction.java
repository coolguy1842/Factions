package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionPlayer;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionPlayerFaction {
    public static void set(FactionsManager manager, UUID id, UUID factionID) {
        if(!manager.playerManager.hasPlayer(id)) return;

        if(factionID != null) manager.database.execute("UPDATE players SET faction = ? WHERE id = ?", factionID.toString(), id.toString());
        else manager.database.execute("UPDATE players SET faction = ? WHERE id = ?", null, id.toString());
    }
}
