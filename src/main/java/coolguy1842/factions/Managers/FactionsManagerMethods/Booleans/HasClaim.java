package coolguy1842.factions.Managers.FactionsManagerMethods.Booleans;

import org.bukkit.Chunk;

import coolguy1842.factions.Managers.FactionsManager;

public class HasClaim {
    public static Boolean has(FactionsManager manager, Chunk chunk) {
        return manager.claimManager.claims.containsKey(chunk);
    }
}
