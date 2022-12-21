package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.UUID;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteFaction {
    public static void delete(FactionsManager manager, UUID id) {
        if(!manager.factionManager.hasFaction(id)) return;

        for(FactionRank rank : manager.rankManager.ranks.values()) {
            if(!rank.getFaction().getID().equals(id)) continue;
        
            manager.rankManager.deleteRank(rank);
        }

        for(FactionPlayer player : manager.playerManager.players.values()) {
            if(!player.getFaction().getID().equals(id)) continue;
        
            player.setFaction(null);
        }
        
        manager.database.execute("DELETE FROM factions WHERE id = ?", id.toString());
        manager.factionManager.factions.remove(id);
    }
}
