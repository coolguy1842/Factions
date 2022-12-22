package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.UUID;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteRank {
    public static void delete(FactionsManager manager, UUID id) {
        if(!manager.rankManager.hasRank(id)) return;
        
        FactionRank rank = manager.rankManager.getRank(id);

        for(FactionPlayer player : rank.players.values()) {
            player.setRank(null);
        }
        
        manager.database.execute("DELETE FROM ranks WHERE id = ?", id.toString());

        rank.getFaction().ranks.remove(id);
        manager.rankManager.ranks.remove(id);
    }
}
