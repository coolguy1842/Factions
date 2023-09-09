package coolguy1842.factions.Menus.Ally;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionMenu;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.Menu;
import coolguy1842.factions.Enums.MenuType;
import coolguy1842.factions.Util.ItemUtil;
import net.kyori.adventure.text.Component;

public class AllyOptionsMenu implements Menu {
    private enum AllyOptionsMenuItems {
        UNALLY,

        ACCEPTINVITE,
        DECLINEINVITE
    }

    private Faction _ally;


    private static ItemStack[] items = new ItemStack[AllyOptionsMenuItems.values().length];
    static {
        items[AllyOptionsMenuItems.UNALLY.ordinal()] = ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Unally", "Unally with this faction");
        
        items[AllyOptionsMenuItems.ACCEPTINVITE.ordinal()] = ItemUtil.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, "Accept Invite", "Become allies with the faction");
        items[AllyOptionsMenuItems.DECLINEINVITE.ordinal()] = ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Decline Invite", "Do not become allies with the faction");
    }

    private Inventory _inventory;
    

    @Override
    public void loadInventory(FactionPlayer p) {
        Inventory menu = Bukkit.createInventory(null, 27, Component.text("Faction Ally Options"));
        if(p.getFaction().isAllied(_ally)) {
            menu.setItem(13, items[AllyOptionsMenuItems.UNALLY.ordinal()]);
        }
        else {
            menu.setItem(11, items[AllyOptionsMenuItems.ACCEPTINVITE.ordinal()]);

            menu.setItem(15, items[AllyOptionsMenuItems.DECLINEINVITE.ordinal()]);
        }

        _inventory = menu;
    }

    @Override
    public Inventory getInventory() { return _inventory; }


    public void setAlly(Faction ally) {
        this._ally = ally;
    }



    public void onClick(InventoryClickEvent e, FactionMenu menu) {
        if(e.getCurrentItem().equals(items[AllyOptionsMenuItems.UNALLY.ordinal()])) {
            menu.getPlayer().getFaction().unally(_ally);

            menu.setMenu(MenuType.ALLY);
        }
        else if(e.getCurrentItem().equals(items[AllyOptionsMenuItems.ACCEPTINVITE.ordinal()])) {
            menu.getPlayer().getFaction().acceptAllyInvite(_ally);

            menu.setMenu(MenuType.ALLY);
        }
        else if(e.getCurrentItem().equals(items[AllyOptionsMenuItems.DECLINEINVITE.ordinal()])) {
            menu.getPlayer().getFaction().removeAllyInvite(_ally);

            menu.setMenu(MenuType.ALLY);
        }
    }

    public void onClose(InventoryCloseEvent e, FactionMenu menu) {
        menu.setMenu(MenuType.ALLY);
    }
}
