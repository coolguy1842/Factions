package coolguy1842.factions.Classes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import coolguy1842.factions.Managers.FactionsManager;

public class FactionClaim {
    private Integer x;
    private Integer z;

    private UUID worldID;
    private Faction faction;

    public FactionClaim(Integer x, Integer z, UUID worldID, UUID factionID) {
        this.x = x;
        this.z = z;

        this.worldID = worldID;
        this.faction = FactionsManager.getInstance().factionManager.getFaction(factionID);
        this.faction.claims.add(this);
    }

    public Integer getX() { return this.x; }
    public Integer getZ() { return this.z; }

    public Faction getFaction() { return this.faction; }
    
    public UUID getWorldID() { return this.worldID; }
    public World getWorld() { return Bukkit.getWorld(this.worldID); }
}
