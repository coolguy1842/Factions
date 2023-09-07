package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class LoadFactionPlayers {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT id FROM players");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                FactionPlayer fP = loadPlayer(manager, UUID.fromString(rows.getString("id")));
                
                fP.formatName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static FactionPlayer loadPlayer(FactionsManager manager, UUID id) {
        CachedRowSet rows = manager.database.query("SELECT * FROM players WHERE id = ?", id.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try {
            rows.next();
            Faction faction = null;
            FactionRank rank = null;

            if(rows.getObject("faction") != null) faction = manager.factionManager.getFaction(UUID.fromString(rows.getString("faction")));
            if(rows.getObject("rank") != null) rank = manager.rankManager.getRank(UUID.fromString(rows.getString("rank")));

            Long money = rows.getLong("money");

            FactionPlayer player = new FactionPlayer(id, faction, rank, money);

            manager.playerManager.players.put(id, player);
            
            if(player.inFaction()) player.getFaction().players.put(id, player);
            if(player.hasRank()) player.getRank().players.put(id, player);

            return player;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
