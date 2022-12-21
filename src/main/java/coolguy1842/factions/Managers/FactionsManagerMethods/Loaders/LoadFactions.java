package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class LoadFactions {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT id FROM factions");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadFaction(manager, UUID.fromString(rows.getString("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Faction loadFaction(FactionsManager manager, UUID id) {
        CachedRowSet rows = manager.database.query("SELECT * FROM factions WHERE id = ?", id.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try {
            rows.next();

            String displayName = rows.getString("displayName");
            UUID leader = UUID.fromString(rows.getString("leader"));

            Long money = rows.getLong("money");

            Faction faction = new Faction(id, displayName, leader, money);

            manager.factionManager.factions.put(id, faction);
            manager.factionManager.factionsNameLookup.put(displayName, id);

            return faction;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Faction loadFaction(FactionsManager manager, String displayName) {
        CachedRowSet rows = manager.database.query("SELECT * FROM factions WHERE displayName = ?", displayName);
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try {
            rows.next();

            UUID id = UUID.fromString(rows.getString("id"));
            UUID leader = UUID.fromString(rows.getString("leader"));

            Long money = rows.getLong("money");

            Faction faction = new Faction(id, displayName, leader, money);

            manager.factionManager.factions.put(id, faction);
            manager.factionManager.factionsNameLookup.put(displayName, id);
            
            return faction;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
