package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionPlayer;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionPlayerRank {
    public static void set(FactionsManager manager, UUID id, UUID rankID) {
        if(!manager.playerManager.hasPlayer(id)) return;

        if(rankID != null) manager.database.execute("UPDATE players SET rank = ? WHERE id = ?", rankID.toString(), id.toString());
        else manager.database.execute("UPDATE players SET rank = ? WHERE id = ?", null, id.toString());
    }
}
