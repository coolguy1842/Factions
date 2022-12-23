package coolguy1842.factions.Managers.FactionsManagerMethods.Deleters;

import java.util.UUID;

import org.bukkit.entity.HumanEntity;

import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;

public class DeleteFactionVault {
    public static void delete(FactionsManager manager, UUID vaultID) {
        if(!manager.vaultManager.hasVault(vaultID)) return;
        FactionVault vault = manager.vaultManager.getVault(vaultID);

        for(HumanEntity player : vault.getInventory().getViewers()) {
            player.closeInventory();
        }

        manager.database.execute("DELETE FROM factionVaults WHERE id = ?", vaultID.toString());

        vault.getFaction().vaults.remove(vaultID);
        vault.getFaction().vaultsByName.remove(vault.getDisplayName());

        manager.vaultManager.vaults.remove(vaultID);
        manager.vaultManager.vaultsNameLookup.remove(vault.getDisplayName());
    }

    public static void delete(FactionsManager manager, String displayName) { delete(manager, manager.vaultManager.vaultsNameLookup.get(displayName)); }
}
