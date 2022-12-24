package coolguy1842.factions.Managers.FactionsManagerMethods.Getters;

import java.util.UUID;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;

public class GetFactionVault {
    public static FactionVault get(FactionsManager manager, UUID id) {
        if(manager.vaultManager.hasVault(id)) return manager.vaultManager.vaults.get(id);
        return null;
    }

    public static FactionVault get(FactionsManager manager, String displayName, Faction faction) {
        if(faction.hasVault(displayName)) return faction.getVault(displayName);
        return null;
    }
}
