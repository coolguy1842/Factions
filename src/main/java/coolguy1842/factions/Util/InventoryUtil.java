package coolguy1842.factions.Util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;

public class InventoryUtil {
    public static String serializeItem(ItemStack itemStack, int slot) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("item", itemStack);

        return config.saveToString();
    }

    public static ItemStack deSerializeItem(String itemStr) {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.loadFromString(itemStr);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return config.getItemStack("item");
    }

    public static String serializeInventory(Inventory inv) {
        YamlConfiguration config = new YamlConfiguration();
        
        config.set("size", inv.getSize());

        for(Integer i = 0; i < inv.getSize(); i++) {
            config.set(i.toString(), inv.getItem(i));
        }

        return config.saveToString();
    }
    
    public static Inventory deSerializeInventory(String invStr, Component title) {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.loadFromString(invStr);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }

        Inventory inv = Bukkit.createInventory(null, config.getInt("size"), title);

        for(Integer i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, config.getItemStack(i.toString()));
        }

        return inv;
    }
}