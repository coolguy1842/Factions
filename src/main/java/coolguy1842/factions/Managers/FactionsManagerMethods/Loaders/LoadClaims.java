package coolguy1842.factions.Managers.FactionsManagerMethods.Loaders;

import java.sql.SQLException;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Managers.FactionsManager;

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

    public static FactionClaim loadClaim(FactionsManager manager, String claimStr, UUID factionID) {
        // check if claim exists
        CachedRowSet rows = manager.database.query("SELECT chunk FROM factionClaims WHERE chunk = ? AND faction = ?", claimStr, factionID.toString());
        if(rows == null) return null;
        else if(rows.size() <= 0) return null;

        try { rows.close(); }
        catch(SQLException e) { e.printStackTrace(); }

        String[] splitStr = claimStr.split(",");

        int chunkX = Integer.parseInt(splitStr[0]);
        int chunkZ = Integer.parseInt(splitStr[1]);

        UUID worldID = UUID.fromString(splitStr[2]);

        FactionClaim claim = new FactionClaim(chunkX, chunkZ, worldID, factionID);

        manager.claimManager.claims.put(claim, claim.getFaction());
        
        return claim;
    }
}
