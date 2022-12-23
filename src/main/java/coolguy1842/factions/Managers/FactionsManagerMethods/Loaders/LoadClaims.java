package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import oshi.util.tuples.Pair;

public class LoadClaims {
    public static void load(FactionsManager manager) {
        CachedRowSet rows = manager.database.query("SELECT chunk, faction FROM factionClaims");
        if(rows == null) return;
        else if(rows.size() <= 0) return;

        try {
            while(rows.next()) {
                loadClaim(manager, rows.getString("chunk"), UUID.fromString(rows.getString("faction")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Pair<Chunk, Faction> loadClaim(FactionsManager manager, String claim, UUID factionID) {
        // check if claim exists
        CachedRowSet rows = manager.database.query("SELECT chunk FROM factionClaims WHERE chunk = ? AND faction = ?", claim, factionID.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try { rows.close(); }
        catch(SQLException e) { e.printStackTrace(); }

        String[] splitStr = claim.split(",");

        int chunkX = Integer.parseInt(splitStr[0]);
        int chunkZ = Integer.parseInt(splitStr[1]);

        UUID worldID = UUID.fromString(splitStr[2]);

        Chunk chunk = Bukkit.getWorld(worldID).getChunkAt(chunkX, chunkZ);
        Faction faction = manager.factionManager.getFaction(factionID);

        manager.claimManager.claims.put(chunk, faction);
        manager.factionManager.getFaction(factionID).claims.add(chunk);
        
        return new Pair<>(chunk, faction);
    }
}
