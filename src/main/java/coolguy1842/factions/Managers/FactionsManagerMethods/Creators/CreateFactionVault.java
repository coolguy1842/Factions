package coolguy1842.factions.Managers.FactionsManagerMethods.Creators;

import java.util.UUID;

import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;

public class CreateFactionVault {
    public static FactionVault create(FactionsManager manager, UUID id, UUID factionID, String displayName, String contents) {
        if(manager.vaultManager.hasVault(id)) return manager.vaultManager.getVault(id);

        manager.database.execute("INSERT INTO factionVaults(id, faction, displayName, contents) VALUES(?, ?, ?, ?)", id.toString(), factionID.toString(), displayName, contents);
        return manager.vaultManager.loadVault(id);
    }
}
