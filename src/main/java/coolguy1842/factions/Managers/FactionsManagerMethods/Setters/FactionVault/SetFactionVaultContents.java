package coolguy1842.factions.Managers.FactionsManagerMethods.Setters.FactionVault;

import java.util.UUID;

import coolguy1842.factions.Managers.FactionsManager;

public class SetFactionVaultContents {
    public SetFactionVaultContents() {}

    public static void set(FactionsManager manager, UUID id, String contents) {
        if(!manager.vaultManager.hasVault(id)) return;

        manager.database.execute("UPDATE factionVaults SET contents = ? WHERE id = ?", contents, id.toString());
    }
}
