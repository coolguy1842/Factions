package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;
import coolguy1842.factions.Managers.FactionsManager;
import oshi.util.tuples.Pair;

public class LoadFactionInvites {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT invited, faction FROM factionInvites");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadInvite(manager, UUID.fromString(rows.getString("invited")), UUID.fromString(rows.getString("faction")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pair<UUID, UUID> loadInvite(FactionsManager manager, UUID id, UUID factionID) {
        // check if invite exists
        CachedRowSet rows = manager.database.query("SELECT invited FROM factionInvites WHERE invited = ? AND faction = ?", id.toString(), factionID.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try { rows.close(); }
        catch(SQLException e) { e.printStackTrace(); }

        Pair<UUID, UUID> invite = new Pair<UUID, UUID>(id, factionID);

        if(!manager.inviteManager.invites.containsKey(id)) manager.inviteManager.invites.put(id, new ArrayList<UUID>());
        manager.inviteManager.invites.get(id).add(factionID);

        manager.factionManager.getFaction(factionID).invites.add(id);
        
        return invite;
    }
}
