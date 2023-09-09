package coolguy1842.factions.Managers.FactionsManagers;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.HumanEntity;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Managers.FactionsManagerMethods.Booleans.HasVault;
import coolguy1842.factions.Managers.FactionsManagerMethods.Creators.CreateFactionVault;
import coolguy1842.factions.Managers.FactionsManagerMethods.Deleters.DeleteFactionVault;
import coolguy1842.factions.Managers.FactionsManagerMethods.Getters.GetFactionVault;
import coolguy1842.factions.Managers.FactionsManagerMethods.Loaders.LoadFactionVaults;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionVault.SetFactionVaultContents;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionVault.SetFactionVaultDisplayName;
import coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionVault.SetFactionVaultFaction;

public class VaultManager {
    public HashMap<UUID, FactionVault> vaults;

    public VaultManager() {
        this.vaults = new HashMap<>();
    }

    public void close() {
        for(FactionVault vault : vaults.values()) {
            List<HumanEntity> players = vault.getInventory().getViewers().stream().toList();
            if(players.size() <= 0) continue;

            for(HumanEntity player : players) {
                player.closeInventory();
            }

            vault.saveInventory();
        }

        this.vaults.clear();

        this.vaults = null;
    }


    public void loadVaults() { LoadFactionVaults.load(FactionsManager.getInstance()); }
    public FactionVault loadVault(UUID vaultID) { return LoadFactionVaults.loadVault(FactionsManager.getInstance(), vaultID); }


    public FactionVault createVault(UUID vaultID, UUID factionID, String displayName, String contents) { 
        return CreateFactionVault.create(FactionsManager.getInstance(), vaultID, factionID, displayName, contents); 
    }

    
    public void deleteVault(UUID vaultID) { DeleteFactionVault.delete(FactionsManager.getInstance(), vaultID); }
    public void deleteVault(String displayName, Faction faction) { DeleteFactionVault.delete(FactionsManager.getInstance(), displayName, faction); }


    public FactionVault getVault(UUID vaultID) { return GetFactionVault.get(FactionsManager.getInstance(), vaultID); }
    public FactionVault getVault(String displayName, Faction faction) { return GetFactionVault.get(FactionsManager.getInstance(), displayName, faction); }

    public Boolean hasVault(UUID vaultID) { return HasVault.has(FactionsManager.getInstance(), vaultID); }
    public Boolean hasVault(String displayName, Faction faction) { return HasVault.has(FactionsManager.getInstance(), displayName, faction); }


    public void setVaultContents(UUID vaultID, String contents) { SetFactionVaultContents.set(FactionsManager.getInstance(), vaultID, contents); }
    public void setVaultContents(FactionVault vault, String contents) { SetFactionVaultContents.set(FactionsManager.getInstance(), vault.getID(), contents); }
    
    public void setVaultDisplayName(UUID vaultID, String displayName) { SetFactionVaultDisplayName.set(FactionsManager.getInstance(), vaultID, displayName); }
    public void setVaultDisplayName(FactionVault vault, String displayName) { SetFactionVaultDisplayName.set(FactionsManager.getInstance(), vault.getID(), displayName); }
    
    public void setVaultFaction(UUID vaultID, UUID factionID) { SetFactionVaultFaction.set(FactionsManager.getInstance(), vaultID, factionID); }
    public void setVaultFaction(FactionVault vault, UUID factionID) { SetFactionVaultFaction.set(FactionsManager.getInstance(), vault.getID(), factionID); }
}
