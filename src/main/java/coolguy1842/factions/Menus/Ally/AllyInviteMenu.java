package coolguy1842.factions.Menus.Ally;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.ItemUtil;
import net.kyori.adventure.text.Component;

public class AllyInviteMenu implements Menu {
    private enum AllyInviteMenuItems {
        BACK
    }

    private static ItemStack[] items = new ItemStack[AllyInviteMenuItems.values().length];
    static {
        items[AllyInviteMenuItems.BACK.ordinal()] = ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Back", "Go back to the ally settings");
    }

    private Inventory _inventory;
    // item slot, faction id
    private Map<Integer, UUID> allyItems;


    @Override
    public void loadInventory(FactionPlayer p) {
        allyItems = new HashMap<>();
        Inventory menu = Bukkit.createInventory(null, 54, Component.text("Ally Invite Menu"));
            
        for(int i = 0; i < 9; i++) {
            if(i == 4) {
                menu.setItem(4, items[AllyInviteMenuItems.BACK.ordinal()]);
                continue;
            }

            menu.setItem(i, ItemUtil.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, ""));
        }

        // TODO: can overflow must add pages later but not a priority as not enough players are on my server to require it
        Faction pFaction = p.getFaction();
        for(Faction f : FactionsManager.getInstance().factionManager.factions.values()) {
            if(f.isAllied(pFaction) || f.hasAllyInvite(pFaction) || pFaction.hasAllyInvite(f) || f.getID().equals(pFaction.getID())) continue;

            ItemStack allyItem = ItemUtil.createItem(Material.PLAYER_HEAD, 1, f.getDisplayName());
            SkullMeta meta = (SkullMeta)allyItem.getItemMeta();

            meta.setOwningPlayer(Bukkit.getOfflinePlayer(f.getLeader()));
            allyItem.setItemMeta(meta);


            Integer slot = menu.firstEmpty();

            allyItems.put(slot, f.getID());
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
        if(e.getCurrentItem().equals(items[AllyInviteMenuItems.BACK.ordinal()])) {
            menu.setMenu(MenuType.ALLY);            
        }
        else if(e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
            FactionsManager.getInstance().factionManager.getFaction(allyItems.get(e.getSlot())).addAllyInvite(menu.getPlayer().getFaction());

            menu.setMenu(MenuType.ALLY);
        }
    }

    public void onClose(InventoryCloseEvent e, FactionMenu menu) {
        menu.setMenu(MenuType.ALLY);
    }
}
