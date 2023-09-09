package coolguy1842.factions.Menus.Ally;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionMenu;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.Menu;
import coolguy1842.factions.Enums.MenuType;
import coolguy1842.factions.Util.ItemUtil;
import net.kyori.adventure.text.Component;

public class AllyMenu implements Menu {
    private enum AllyMenuItems {
        INVITE,
        INVITES,
        UNINVITE
    }

    private static ItemStack[] items = new ItemStack[AllyMenuItems.values().length];
    static {
        items[AllyMenuItems.INVITE.ordinal()] = ItemUtil.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, "Invite", "Invite factions to be allies");
        items[AllyMenuItems.INVITES.ordinal()] = ItemUtil.createItem(Material.YELLOW_STAINED_GLASS_PANE, 1, "Invites", "Accept or deny any invite your faction has to be allies");
        items[AllyMenuItems.UNINVITE.ordinal()] = ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Remove Invite", "Remove an invite to be allies with a faction");
    }

    private Inventory _inventory;
    // item slot, faction
    private Map<Integer, Faction> allyItems;


    @Override
    public void loadInventory(FactionPlayer p) {
        allyItems = new HashMap<>();
        Inventory menu = Bukkit.createInventory(null, 54, Component.text("Ally Menu"));
            
        for(int i = 0; i < 9; i++) {
            if(i == 3) {
                menu.setItem(3, items[AllyMenuItems.INVITE.ordinal()]);
                continue;
            }
            else if(i == 4) {
                menu.setItem(4, items[AllyMenuItems.INVITES.ordinal()]);
                continue;
            }
            else if(i == 5) {
                menu.setItem(5, items[AllyMenuItems.UNINVITE.ordinal()]);
                continue;
            }

            menu.setItem(i, ItemUtil.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, ""));
        }

        // TODO: can overflow must add pages later but not a priority as not enough players are on my server to require it
        for(Faction f : p.getFaction().allies) {
            ItemStack allyItem = ItemUtil.createItem(Material.PLAYER_HEAD, 1, f.getDisplayName());
            SkullMeta meta = (SkullMeta)allyItem.getItemMeta();

            meta.setOwningPlayer(Bukkit.getOfflinePlayer(f.getLeader()));
            allyItem.setItemMeta(meta);

            Integer slot = menu.firstEmpty();

            allyItems.put(slot, f);
            menu.setItem(slot, allyItem);
        }


        for(int i = menu.getSize() - 9; i < menu.getSize(); i++) {
            menu.setItem(i, ItemUtil.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, ""));
        }

        _inventory = menu;
    }

    @Override
    public Inventory getInventory() { return _inventory; }


    public void onClick(InventoryClickEvent e, FactionMenu menu) {
        if(e.getCurrentItem().equals(items[AllyMenuItems.INVITE.ordinal()])) {
            menu.setMenu(MenuType.ALLYINVITE);
        }
        else if(e.getCurrentItem().equals(items[AllyMenuItems.INVITES.ordinal()])) {
            menu.setMenu(MenuType.ALLYINVITES);
        }
        else if(e.getCurrentItem().equals(items[AllyMenuItems.UNINVITE.ordinal()])) {
            menu.setMenu(MenuType.ALLYUNINVITE);
        }
        else if(e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
            AllyOptionsMenu allyOptionsMenu = (AllyOptionsMenu)menu.getMenu(MenuType.ALLYOPTIONS);
            allyOptionsMenu.setAlly(allyItems.get(e.getSlot()));

            menu.setMenu(MenuType.ALLYOPTIONS);
        }
    }

    public void onClose(InventoryCloseEvent e, FactionMenu menu) {
        menu.setMenu(MenuType.BASE);
    }
}
