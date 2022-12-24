package coolguy1842.factions.Classes;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.Base64Util;
import coolguy1842.factions.Util.InventoryUtil;
import net.kyori.adventure.text.Component;

public class FactionVault {
    private UUID id;

    private Faction faction;    
    private String displayName;

    private Inventory inventory;
    private String contents;
    
    public FactionVault(UUID id, Faction faction, String displayName, String contents) {
        this.id = id;

        this.faction = faction;
        this.displayName = displayName;

        this.contents = contents;
        this.loadInventory();
    }

    public void saveInventory() {
        this.contents = Base64Util.strToBase64(InventoryUtil.serializeInventory(inventory));

        FactionsManager.getInstance().vaultManager.setVaultContents(this, this.contents);
    }

    public void loadInventory() {
        List<HumanEntity> viewers = null;
        if(this.inventory != null) viewers = this.inventory.getViewers();

        this.inventory = InventoryUtil.deSerializeInventory(Base64Util.base64ToStr(this.contents), this.faction.getFormattedDisplayName()
                                                                                                    .append(Component.text(" - " + this.displayName)));
        
        if(viewers != null) {
            for(HumanEntity viewer : viewers) {
                viewer.openInventory(this.inventory);
            }
        }
    }


    public void dropAllContents(Location location) {
        for(ItemStack itemStack : this.inventory.getContents()) {
            if(itemStack == null) continue;

            location.getWorld().dropItem(location, itemStack);
        }
    }


    public UUID getID() { return this.id; }
    public String getDisplayName() { return this.displayName; }
    public Faction getFaction() { return this.faction; }
    public Inventory getInventory() { return this.inventory; }


    public void setFaction(UUID factionID) { 
        if(FactionsManager.getInstance().factionManager.hasFaction(factionID)) return;

        this.faction.vaults.remove(this.id);
        this.faction.vaultsByName.remove(this.displayName);

        this.faction = FactionsManager.getInstance().factionManager.getFaction(factionID);
        FactionsManager.getInstance().vaultManager.setVaultFaction(this, factionID); 
        
        this.faction.vaults.put(this.id, this);
        this.faction.vaultsByName.put(this.displayName, this);
    }
    
    public void setDisplayName(String displayName) { 
        this.saveInventory();

        this.faction.vaultsByName.remove(this.displayName);
        
        this.displayName = displayName;

        this.faction.vaultsByName.put(this.displayName, this);
        FactionsManager.getInstance().vaultManager.setVaultDisplayName(this, displayName); 
        
        this.loadInventory();
    }
}
