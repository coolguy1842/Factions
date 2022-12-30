package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import org.bukkit.Chunk;

import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Managers.FactionsManager;

public class CreateClaim {
    public static FactionClaim create(FactionsManager manager, Chunk chunk, UUID faction) {
        if(manager.claimManager.hasClaim(chunk)) return null;

        manager.database.execute("INSERT INTO factionClaims(chunk, faction) VALUES(?, ?)", 
                                    chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getUID().toString(), 
                                    faction.toString());

        FactionClaim claim =  manager.claimManager.loadClaim(chunk, faction);
        manager.claimManager.cachedClaims.put(chunk, claim);

        return claim;
    }
}
