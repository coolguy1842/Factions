package coolguy1842.factions.Managers.FactionsManagers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Managers.FactionsManagerMethods.Booleans.HasClaim;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateClaim;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteClaim;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetClaim;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadClaims;

public class ClaimManager {
    public HashMap<FactionClaim, Faction> claims;
    public HashMap<Chunk, FactionClaim> cachedClaims;
    public HashMap<UUID, Boolean> autoClaimPlayers;

    public ClaimManager() {
        this.claims = new HashMap<>();
        this.cachedClaims = new HashMap<>();
        this.autoClaimPlayers = new HashMap<>();
    }

    public void close() {
        this.claims.clear();
        this.cachedClaims.clear();
        this.autoClaimPlayers.clear();

        this.claims = null;
        this.cachedClaims = null;
        this.autoClaimPlayers = null;
    }

    public void loadClaims() { LoadClaims.load(FactionsManager.getInstance()); }
    public FactionClaim loadClaim(Chunk chunk, UUID faction) { 
        return LoadClaims.loadClaim(FactionsManager.getInstance(), chunk.getX() + "," + chunk.getZ() + "," + chunk.getWorld().getUID().toString(), faction); 
    }


    public Boolean hasClaim(Chunk chunk) { return HasClaim.has(FactionsManager.getInstance(), chunk); }
    public FactionClaim getClaim(Chunk chunk) { return GetClaim.get(FactionsManager.getInstance(), chunk); }
    
    public Boolean playerHasAutoClaim(Player p) {
        if(!this.autoClaimPlayers.containsKey(p.getUniqueId())) return false;
        return this.autoClaimPlayers.get(p.getUniqueId());
    }
    
    public void setPlayerHasAutoClaim(Player p, Boolean state) {
        if(!this.autoClaimPlayers.containsKey(p.getUniqueId())) this.autoClaimPlayers.put(p.getUniqueId(), state);
        else this.autoClaimPlayers.replace(p.getUniqueId(), state);
    }

    
    public FactionClaim createClaim(Chunk chunk, UUID faction) { return CreateClaim.create(FactionsManager.getInstance(), chunk, faction); }
    

    public void deleteClaim(Chunk chunk) { DeleteClaim.delete(FactionsManager.getInstance(), chunk); }


}
