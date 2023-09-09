package coolguy1842.factions.Menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import coolguy1842.factions.Classes.FactionMenu;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.Menu;
import coolguy1842.factions.Enums.MenuType;
import coolguy1842.factions.Util.ItemUtil;
import net.kyori.adventure.text.Component;

public class BaseMenu implements Menu {
    private enum BaseMenuItems {
        ALLIES
    }

    private static ItemStack[] items = new ItemStack[BaseMenuItems.values().length];
    static {
        items[BaseMenuItems.ALLIES.ordinal()] = ItemUtil.createItem(Material.IRON_SWORD, 1, "Allies", "Ally settings");
    }

    private Inventory _inventory;


    @Override
    public void loadInventory(FactionPlayer p) {
        Inventory menu = Bukkit.createInventory(null, 54, Component.text("Faction Menu"));
        if(p.isLeader()) {
            menu.setItem(0, items[BaseMenuItems.ALLIES.ordinal()]);
        }

        _inventory = menu;
    }

    @Override
    public Inventory getInventory() { return _inventory; }


    public void onClick(InventoryClickEvent e, FactionMenu menu) {
        if(e.getCurrentItem().equals(items[BaseMenuItems.ALLIES.ordinal()])) {
            menu.setMenu(MenuType.ALLY);
        }
    }

    public void onClose(InventoryCloseEvent e, FactionMenu menu) {
        if(menu.getMenuType() != MenuType.BASE) return;
        
        menu.setOpen(false);
    }
}
