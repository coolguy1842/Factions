package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import org.bukkit.Chunk;

import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteClaim {
    public static void delete(FactionsManager manager, Chunk chunk) {
        if(!manager.claimManager.hasClaim(chunk)) return;

        FactionClaim claim = manager.claimManager.getClaim(chunk);

        claim.getFaction().claims.remove(claim);
        manager.claimManager.claims.remove(claim);
        manager.claimManager.cachedClaims.put(chunk, null);

        manager.database.execute("DELETE FROM factionClaims WHERE chunk = ?", claim.getX() + "," + claim.getZ() + "," + claim.getWorld().getUID().toString());
    }
}
