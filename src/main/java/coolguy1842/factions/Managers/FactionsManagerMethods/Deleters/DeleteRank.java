package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.List;
import java.util.UUID;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteRank {
    public static void delete(FactionsManager manager, UUID id) {
        if(!manager.rankManager.hasRank(id)) return;
        
        FactionRank rank = manager.rankManager.getRank(id);
        List<FactionPlayer> players = rank.players.values().stream().toList();

        for(FactionPlayer player : players) {
            player.setRank(null);
        }
                        
        manager.database.execute("DELETE FROM factionRanks WHERE id = ?", id.toString());

        rank.getFaction().ranks.remove(id);
        rank.getFaction().ranksByName.remove(rank.getDisplayName());
        
        manager.rankManager.ranks.remove(id);
    }
}
