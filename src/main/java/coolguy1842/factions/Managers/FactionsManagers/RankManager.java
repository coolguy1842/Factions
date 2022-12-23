package coolguy1842.factions.Managers.FactionsManagers;

import java.util.HashMap;
import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Managers.FactionsManagerMethods.Booleans.HasRank;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateFactionRank;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteRank;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetFactionRank;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadFactionRanks;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank.SetFactionRankDisplayName;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank.SetFactionRankFaction;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank.SetFactionRankIsDefault;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionRank.SetFactionRankPermissions;

public class RankManager {
    public HashMap<UUID, FactionRank> ranks;

    public RankManager() {
        ranks = new HashMap<>();
    }

    public void close() {
        this.ranks.clear();
        this.ranks = null;
    }
    
    public void loadRanks() { LoadFactionRanks.load(FactionsManager.getInstance()); }
    public FactionRank loadRank(UUID id) { return LoadFactionRanks.loadRank(FactionsManager.getInstance(), id); }    


    public FactionRank getRank(UUID id) { return GetFactionRank.get(FactionsManager.getInstance(), id); }


    public FactionRank createRank(UUID id, Faction faction, String displayName, Boolean isDefault, String... permissions) { 
        return CreateFactionRank.create(FactionsManager.getInstance(), id, faction, displayName, isDefault, permissions); 
    }
    

    public void deleteRank(FactionRank rank) { DeleteRank.delete(FactionsManager.getInstance(), rank.getID()); }
    public void deleteRank(UUID id) { DeleteRank.delete(FactionsManager.getInstance(), id); }

    
    public void setRankDisplayName(FactionRank rank, String displayName) { SetFactionRankDisplayName.set(FactionsManager.getInstance(), rank.getID(), displayName); }
    public void setRankDisplayName(UUID id, String displayName) { SetFactionRankDisplayName.set(FactionsManager.getInstance(), id, displayName); }

    public void setRankFaction(FactionRank rank, UUID factionID) { SetFactionRankFaction.set(FactionsManager.getInstance(), rank.getID(), factionID); }
    public void setRankFaction(UUID id, UUID factionID) { SetFactionRankFaction.set(FactionsManager.getInstance(), id, factionID); }

    public void setRankIsDefault(FactionRank rank, Boolean isDefault) { SetFactionRankIsDefault.set(FactionsManager.getInstance(), rank.getID(), isDefault); }
    public void setRankIsDefault(UUID id, Boolean isDefault) { SetFactionRankIsDefault.set(FactionsManager.getInstance(), id, isDefault); }

    public void setRankPermissions(FactionRank rank, String permissions) { SetFactionRankPermissions.set(FactionsManager.getInstance(), rank.getID(), permissions); }
    public void setRankPermissions(UUID id, String permissions) { SetFactionRankPermissions.set(FactionsManager.getInstance(), id, permissions); }

    
    public Boolean hasRank(UUID id) { return HasRank.has(FactionsManager.getInstance(), id); }
    
}
