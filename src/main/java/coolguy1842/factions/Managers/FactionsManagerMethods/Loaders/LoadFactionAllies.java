package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import oshi.util.tuples.Pair;

public class LoadFactionAllies {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT faction1, faction2 FROM factionAllies");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadAlly(manager, UUID.fromString(rows.getString("faction1")), UUID.fromString(rows.getString("faction2")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pair<Faction, Faction> loadAlly(FactionsManager manager, UUID faction1ID, UUID faction2ID) {
        // check if ally exists
        CachedRowSet rows = manager.database.query("SELECT faction1, faction2 FROM factionAllies WHERE faction1 = ? AND faction2 = ?", faction1ID.toString(), faction2ID.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try { rows.close(); }
        catch(SQLException e) { e.printStackTrace(); }

        Pair<Faction, Faction> ally = new Pair<Faction, Faction>(manager.factionManager.getFaction(faction1ID), manager.factionManager.getFaction(faction2ID));

        ally.getA().allies.add(ally.getB());
        ally.getB().allies.add(ally.getA());
        
        return ally;
    }
}
