package coolguy1842.factions.Managers.FactionsManagers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Managers.FactionsManagerMethods.Booleans.HasFaction;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateFaction;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateFactionAllyInvite;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteFaction;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteFactionAlly;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteFactionAllyInvite;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteFactionOption;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetFaction;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetFactionOptions;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadFactionAllies;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadFactionAllyInvites;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadFactions;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction.MakeFactionsAllies;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction.SetFactionDisplayName;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction.SetFactionLeader;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction.SetFactionMoney;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.Faction.SetFactionOption;

public class FactionManager {
    public HashMap<UUID, Faction> factions;
    public HashMap<String, UUID> factionsNameLookup;

    public FactionManager() {
        factions = new HashMap<>();
        factionsNameLookup = new HashMap<>();
    }

    public void close() {
        this.factions.clear();
        this.factionsNameLookup.clear();

        this.factions = null;
        this.factionsNameLookup = null;
    }

    public void loadFactions() { 
        LoadFactions.load(FactionsManager.getInstance()); 
        LoadFactionAllies.load(FactionsManager.getInstance());
        LoadFactionAllyInvites.load(FactionsManager.getInstance());
    }
    
    public Faction loadFaction(UUID id) { return LoadFactions.loadFaction(FactionsManager.getInstance(), id); }
    public Faction loadFaction(String displayName) { return LoadFactions.loadFaction(FactionsManager.getInstance(), displayName); }


    public Faction createFaction(UUID id, String displayName, Player leader, Long money) { 
        return CreateFaction.create(FactionsManager.getInstance(), id, displayName, leader, money); 
    }
    
    public void createFactionAllyInvite(Faction inviter, Faction invited) { CreateFactionAllyInvite.create(FactionsManager.getInstance(), inviter, invited); }
    public void createFactionAlly(Faction faction1, Faction faction2) { MakeFactionsAllies.set(FactionsManager.getInstance(), faction1, faction2); }
    
    
    public Boolean hasFaction(UUID id) { return HasFaction.has(FactionsManager.getInstance(), id); }
    public Boolean hasFaction(String displayName) { return HasFaction.has(FactionsManager.getInstance(), displayName); }
    
    public Faction getFaction(UUID id) { return GetFaction.get(FactionsManager.getInstance(), id); }
    public Faction getFaction(String displayName) { return GetFaction.get(FactionsManager.getInstance(), displayName); }

    public HashMap<String, String> getFactionOptions(UUID factionID) { return GetFactionOptions.get(FactionsManager.getInstance(), factionID); }
    public HashMap<String, String> getFactionOptions(Faction faction) { return GetFactionOptions.get(FactionsManager.getInstance(), faction.getID()); }
    
    
    public void deleteFaction(Faction faction) { DeleteFaction.delete(FactionsManager.getInstance(), faction.getID()); }
    public void deleteFaction(UUID id) { DeleteFaction.delete(FactionsManager.getInstance(), id); }
    
    public void deleteFactionOption(Faction faction, String option) { DeleteFactionOption.delete(FactionsManager.getInstance(), option, faction.getID()); }
    public void deleteFactionOption(UUID id, String option) { DeleteFactionOption.delete(FactionsManager.getInstance(), option, id); }
    

    public void deleteFactionAllyInvite(Faction inviter, Faction invited) { DeleteFactionAllyInvite.delete(FactionsManager.getInstance(), inviter, invited); }
    public void deleteFactionAlly(Faction faction1, Faction faction2) { DeleteFactionAlly.delete(FactionsManager.getInstance(), faction1, faction2); }
    

    
    public void setFactionMoney(Faction faction, Long money) { SetFactionMoney.set(FactionsManager.getInstance(), faction.getID(), money); }
    public void setFactionMoney(UUID id, Long money) { SetFactionMoney.set(FactionsManager.getInstance(), id, money); }

    public void setFactionLeader(Faction faction, UUID leader) { SetFactionLeader.set(FactionsManager.getInstance(), faction.getID(), leader); }
    public void setFactionLeader(UUID id, UUID leader) { SetFactionLeader.set(FactionsManager.getInstance(), id, leader); }
    
    public void setFactionDisplayName(Faction faction, String displayName) { SetFactionDisplayName.set(FactionsManager.getInstance(), faction.getID(), displayName); }
    public void setFactionDisplayName(UUID id, String displayName) { SetFactionDisplayName.set(FactionsManager.getInstance(), id, displayName); }
    
    public void setFactionOption(Faction faction, String option, String value) { SetFactionOption.set(FactionsManager.getInstance(), value, option, faction.getID()); }
    public void setFactionOption(UUID id, String option, String value) { SetFactionOption.set(FactionsManager.getInstance(), value, option, id); }

}
