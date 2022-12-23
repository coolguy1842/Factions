package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionVault;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionVaultFaction {
    public static void set(FactionsManager manager, UUID id, UUID factionID) {
        if(!manager.vaultManager.hasVault(id)) return;

        manager.database.execute("UPDATE factionVaults SET faction = ? WHERE id = ?", factionID.toString(), id.toString());
    }
}
