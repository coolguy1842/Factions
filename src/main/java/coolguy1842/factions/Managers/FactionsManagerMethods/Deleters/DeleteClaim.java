package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import org.bukkit.Chunk;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteClaim {
    public static void delete(FactionsManager manager, Chunk claim) {
        if(!manager.claimManager.hasClaim(claim)) return;

        Faction faction = manager.claimManager.getClaim(claim);

        faction.claims.remove(claim);
        manager.claimManager.claims.remove(claim);

        manager.database.execute("DELETE FROM factionClaims WHERE chunk = ?", claim.getX() + "," + claim.getZ() + "," + claim.getWorld().getUID().toString());
    }
}
