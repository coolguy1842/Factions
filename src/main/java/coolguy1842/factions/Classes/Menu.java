package coolguy1842.factions.Classes;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public interface Menu {
    Inventory getInventory();
    void loadInventory(FactionPlayer p);

    void onClick(InventoryClickEvent e, FactionMenu menu);
    void onClose(InventoryCloseEvent e, FactionMenu menu);
}
