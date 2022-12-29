package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import org.bukkit.Chunk;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;

public class GetClaim {
    public GetClaim() {}

    public static Faction get(FactionsManager manager, Chunk chunk) {
        if(manager.claimManager.hasClaim(chunk)) return manager.claimManager.claims.get(chunk);
        
        return null;
    }

}
