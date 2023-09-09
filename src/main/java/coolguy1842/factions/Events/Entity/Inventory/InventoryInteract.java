package coolguy1842.factions.Events.Entity.Inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class InventoryInteract implements Listener {
    @EventHandler
    private void onInventoryInteract(InventoryClickEvent e) {
        if(e == null) return;

        FactionPlayer fP = FactionsManager.getInstance().playerManager.getPlayer(e.getWhoClicked().getUniqueId());

        if(fP.getMenu().isOpen()) {
            fP.getMenu().onInventoryClick(e);
        }
    }
}
