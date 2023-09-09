package coolguy1842.factions.Classes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Enums.MenuType;
import coolguy1842.factions.Menus.BaseMenu;
import coolguy1842.factions.Menus.Ally.AllyInviteMenu;
import coolguy1842.factions.Menus.Ally.AllyInvitesMenu;
import coolguy1842.factions.Menus.Ally.AllyMenu;
import coolguy1842.factions.Menus.Ally.AllyOptionsMenu;
import coolguy1842.factions.Menus.Ally.AllyUninviteMenu;

public class FactionMenu {
    private Menu[] _menus = new Menu[MenuType.values().length];
    private MenuType _currentMenu;

    private Inventory _currentInventory;

    private FactionPlayer _player;
    private Boolean _isOpen;

    public FactionMenu(FactionPlayer player) {
        _player = player;
        _isOpen = false;
    }


    public void loadMenus() {
        if(_player == null || !_player.inFaction()) return;
        
        _menus[MenuType.BASE.ordinal()] = new BaseMenu();
        _menus[MenuType.ALLY.ordinal()] = new AllyMenu();
        _menus[MenuType.ALLYOPTIONS.ordinal()] = new AllyOptionsMenu();
        _menus[MenuType.ALLYINVITE.ordinal()] = new AllyInviteMenu();
        _menus[MenuType.ALLYINVITES.ordinal()] = new AllyInvitesMenu();
        _menus[MenuType.ALLYUNINVITE.ordinal()] = new AllyUninviteMenu();
    }

    public Menu getMenu(MenuType type) { return this._menus[type.ordinal()]; }
    public MenuType getMenuType() { return this._currentMenu; }

    public FactionPlayer getPlayer() { return this._player; }
    public void setMenu(MenuType type) {
        Player p = _player.getPlayer();


        _menus[type.ordinal()].loadInventory(_player);
        _currentInventory = _menus[type.ordinal()].getInventory();

        Bukkit.getScheduler().runTask(Globals.plugin, () -> {
            p.openInventory(_currentInventory);
            _currentMenu = type;
            
            this._isOpen = true;
        });
    }


    public Boolean isOpen() { return this._isOpen; }
    public void setOpen(Boolean open) { _isOpen = open; }



    public void onInventoryClick(InventoryClickEvent e) {
        if(e == null || e.getWhoClicked() == null || e.getCurrentItem() == null || _currentMenu == null) return;
        if(!_currentInventory.equals(e.getClickedInventory())) return;
        e.setCancelled(true);

        switch(_currentMenu) {
        case BASE:         _menus[MenuType.BASE.ordinal()].onClick(e, this);            break;
        case ALLY:         _menus[MenuType.ALLY.ordinal()].onClick(e, this);            break;
        case ALLYOPTIONS:  _menus[MenuType.ALLYOPTIONS.ordinal()].onClick(e, this);     break;
        case ALLYINVITE:   _menus[MenuType.ALLYINVITE.ordinal()].onClick(e, this);      break;
        case ALLYINVITES:  _menus[MenuType.ALLYINVITES.ordinal()].onClick(e, this);     break;
        case ALLYUNINVITE: _menus[MenuType.ALLYUNINVITE.ordinal()].onClick(e, this);    break;
        case BANK:         _menus[MenuType.BANK.ordinal()].onClick(e, this);            break;
        case SETTINGS:     _menus[MenuType.SETTINGS.ordinal()].onClick(e, this);        break;
        case PLAYER:       _menus[MenuType.PLAYER.ordinal()].onClick(e, this);          break;
        case RANK:         _menus[MenuType.RANK.ordinal()].onClick(e, this);            break;
        default: break;
        }
    }

    public void onInventoryClose(InventoryCloseEvent e) {
        if(e == null || e.getPlayer() == null || _currentMenu == null) return;
        if(!_currentInventory.equals(e.getInventory()) && !_currentInventory.equals(_menus[_currentMenu.ordinal()].getInventory())) return;

        switch(_currentMenu) {
        case BASE:         _menus[MenuType.BASE.ordinal()].onClose(e, this);            break;
        case ALLY:         _menus[MenuType.ALLY.ordinal()].onClose(e, this);            break;
        case ALLYOPTIONS:  _menus[MenuType.ALLYOPTIONS.ordinal()].onClose(e, this);     break;
        case ALLYINVITE:   _menus[MenuType.ALLYINVITE.ordinal()].onClose(e, this);      break;
        case ALLYINVITES:  _menus[MenuType.ALLYINVITES.ordinal()].onClose(e, this);     break;
        case ALLYUNINVITE: _menus[MenuType.ALLYUNINVITE.ordinal()].onClose(e, this);    break;
        case BANK:         _menus[MenuType.BANK.ordinal()].onClose(e, this);            break;
        case SETTINGS:     _menus[MenuType.SETTINGS.ordinal()].onClose(e, this);        break;
        case PLAYER:       _menus[MenuType.PLAYER.ordinal()].onClose(e, this);          break;
        case RANK:         _menus[MenuType.RANK.ordinal()].onClose(e, this);            break;
        default: break;
        }
    }
}
