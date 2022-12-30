package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import org.bukkit.Chunk;

import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Managers.FactionsManager;

public class GetClaim {
    public GetClaim() {}
    
    public static FactionClaim get(FactionsManager manager, Chunk chunk) {
        if(manager.claimManager.cachedClaims.containsKey(chunk)) return manager.claimManager.cachedClaims.get(chunk);

        for(FactionClaim claim : manager.claimManager.claims.keySet()) {
            if(claim.getX() == chunk.getX() && claim.getZ() == chunk.getZ() && claim.getWorldID().equals(chunk.getWorld().getUID())) {
                manager.claimManager.cachedClaims.put(chunk, claim);
                return claim;
            }
        }
        
        manager.claimManager.cachedClaims.put(chunk, null);
        return null;
    }
}
