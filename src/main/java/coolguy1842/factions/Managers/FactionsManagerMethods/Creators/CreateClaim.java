package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import org.bukkit.Chunk;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import oshi.util.tuples.Pair;

public class CreateClaim {
    public static Pair<Chunk, Faction> create(FactionsManager manager, Chunk chunk, UUID faction) {
        if(manager.claimManager.hasClaim(chunk)) return null;

        manager.database.execute("INSERT INTO factionClaims(chunk, faction) VALUES(?, ?)", 
                                    chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getUID().toString(), 
                                    faction.toString());

        return manager.claimManager.loadClaim(chunk, faction);
    }
}
