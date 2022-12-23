package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;

public class LoadFactionRanks {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT id FROM factionRanks");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadRank(manager, UUID.fromString(rows.getString("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static FactionRank loadRank(FactionsManager manager, UUID id) {
        CachedRowSet rows = manager.database.query("SELECT * FROM factionRanks WHERE id = ?", id.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try {
            rows.next();

            Faction faction = manager.factionManager.getFaction(UUID.fromString(rows.getString("faction")));

            String displayName = rows.getString("displayName");
            Boolean isDefault = rows.getBoolean("isDefault");

            String permissions = "";
            if(rows.getObject("permissions") != null) permissions = rows.getString("permissions");

            FactionRank rank = new FactionRank(id, faction, displayName, isDefault, permissions.split(","));
            manager.rankManager.ranks.put(id, rank);

            if(isDefault) faction.setDefaultRank(id);
            faction.ranks.put(id, rank);

            return rank;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
