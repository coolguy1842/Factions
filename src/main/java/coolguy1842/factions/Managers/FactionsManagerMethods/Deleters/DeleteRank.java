package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.UUID;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteRank {
    public static void delete(FactionsManager manager, UUID id) {
        if(!manager.rankManager.hasRank(id)) return;
        
        for(FactionPlayer player : manager.playerManager.players.values()) {
            if(!player.getRank().getID().equals(id)) continue;
        
            player.setRank(null);
        }
        
        manager.database.execute("DELETE FROM ranks WHERE id = ?", id.toString());

        manager.rankManager.ranks.remove(id);
    }
}
