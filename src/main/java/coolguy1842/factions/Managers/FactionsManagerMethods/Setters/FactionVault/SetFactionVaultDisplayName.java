package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionVault;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionVaultDisplayName {
    public static void set(FactionsManager manager, UUID id, String displayName) {
        if(!manager.vaultManager.hasVault(id)) return;

        manager.database.execute("UPDATE factionVaults SET displayName = ? WHERE id = ?", displayName, id.toString());
    }
}
