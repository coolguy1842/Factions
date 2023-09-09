package coolguy1842.factions.Events.Entity.Inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionVault;
import coolguy1842.factions.Managers.FactionsManager;

public class InventoryClose implements Listener {
    @EventHandler
    private void onInventoryClose(InventoryCloseEvent e) {
        if(e == null) return;

        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(e.getPlayer().getUniqueId());
        if(player == null || !player.inFaction()) return;

        
        if(player.getMenu().isOpen()) {
            player.getMenu().onInventoryClose(e);
            
            return;
        }

        for(FactionVault vault : player.getFaction().vaults.values()) {
            if(vault.getInventory() == e.getInventory()) {
                vault.saveInventory();
                return;
            }
        }
    }
}
