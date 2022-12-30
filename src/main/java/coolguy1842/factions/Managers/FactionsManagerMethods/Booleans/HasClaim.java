package coolguy1842.factions.Managers.FactionsManagerMethods.Booleans;

import org.bukkit.Chunk;

import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Managers.FactionsManager;

public class HasClaim {
    public static Boolean has(FactionsManager manager, Chunk chunk) {
        if(manager.claimManager.cachedClaims.containsKey(chunk)) {
            return manager.claimManager.cachedClaims.get(chunk) != null;
        }

        for(FactionClaim claim : manager.claimManager.claims.keySet()) {
            if(claim.getX() == chunk.getX() && claim.getZ() == chunk.getZ() && claim.getWorldID().equals(chunk.getWorld().getUID())) {
                manager.claimManager.cachedClaims.put(chunk, claim);
                return true;
            }
        }

        manager.claimManager.cachedClaims.put(chunk, null);
        return false;
    }
}
