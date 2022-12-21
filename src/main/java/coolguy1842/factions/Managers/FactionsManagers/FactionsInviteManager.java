package coolguy1842.factions.Managers.FactionsManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Managers.FactionsManagerMethods.Booleans.HasFactionInvite;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateFactionInvite;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteFactionInvite;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetFactionPlayerInvites;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadFactionInvites;
import oshi.util.tuples.Pair;

public class FactionsInviteManager {
    public HashMap<UUID, ArrayList<UUID>> invites;

    public FactionsInviteManager() {
        invites = new HashMap<>();
    }

    public void close() {
        invites.clear();
        invites = null;
    }

    
    public void loadInvites() { LoadFactionInvites.load(FactionsManager.getInstance()); }
    public Pair<UUID, UUID> loadInvite(UUID id, Faction faction) { return LoadFactionInvites.loadInvite(FactionsManager.getInstance(), id, faction.getID()); }
    public Pair<UUID, UUID> loadInvite(UUID id, UUID faction) { return LoadFactionInvites.loadInvite(FactionsManager.getInstance(), id, faction); }
    

    public void createFactionInvite(Player invited, UUID factionID) { CreateFactionInvite.create(FactionsManager.getInstance(), invited.getUniqueId(), factionID); }
    public void createFactionInvite(UUID invited, UUID factionID) { CreateFactionInvite.create(FactionsManager.getInstance(), invited, factionID); }

        
    public void deletePlayerInvite(Player player, UUID factionID) { DeleteFactionInvite.delete(FactionsManager.getInstance(), player.getUniqueId(), factionID); }
    public void deletePlayerInvite(UUID id, UUID factionID) { DeleteFactionInvite.delete(FactionsManager.getInstance(), id, factionID); }

    public void deleteAllPlayerInvites(Player player) { DeleteFactionInvite.deleteAllPlayerInvites(FactionsManager.getInstance(), player.getUniqueId()); }
    public void deleteAllPlayerInvites(UUID id) { DeleteFactionInvite.deleteAllPlayerInvites(FactionsManager.getInstance(), id); }


    public Boolean playerHasInvite(Player player, UUID factionID) { return HasFactionInvite.has(FactionsManager.getInstance(), player.getUniqueId(), factionID); } 
    public Boolean playerHasInvite(UUID id, UUID factionID) { return HasFactionInvite.has(FactionsManager.getInstance(), id, factionID); } 

    public ArrayList<UUID> getPlayerInvites(Player player) { return GetFactionPlayerInvites.get(FactionsManager.getInstance(), player.getUniqueId()); }
    public ArrayList<UUID> getPlayerInvites(UUID id) { return GetFactionPlayerInvites.get(FactionsManager.getInstance(), id); }
}