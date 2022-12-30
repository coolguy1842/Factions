package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import oshi.util.tuples.Pair;

public class LoadFactionAllyInvites {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT inviter, invited FROM factionAllyInvites");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadAllyInvite(manager, UUID.fromString(rows.getString("inviter")), UUID.fromString(rows.getString("invited")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pair<Faction, Faction> loadAllyInvite(FactionsManager manager, UUID inviterID, UUID invitedID) {
        // check if ally exists
        CachedRowSet rows = manager.database.query("SELECT inviter FROM factionAllyInvites WHERE inviter = ? AND invited = ?", inviterID.toString(), invitedID.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try { rows.close(); }
        catch(SQLException e) { e.printStackTrace(); }

        Pair<Faction, Faction> allyInvite = new Pair<Faction, Faction>(manager.factionManager.getFaction(inviterID), manager.factionManager.getFaction(invitedID));

        allyInvite.getB().allyInvites.add(allyInvite.getA());
        
        return allyInvite;
    }
}
