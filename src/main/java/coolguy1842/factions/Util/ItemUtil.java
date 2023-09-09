package coolguy1842.factions.Util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public final class ItemUtil {
    public static ItemStack createItem(Material type, int amount, String name, String... lore) {
        ItemStack itemStack = new ItemStack(type);
        ItemMeta meta = itemStack.getItemMeta();

        itemStack.setAmount(amount);

        meta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));

        ArrayList<Component> loreComponents = new ArrayList<Component>();

        for(int i = 0; i < lore.length; i++) {
            loreComponents.add(Component.text(lore[i]).decoration(TextDecoration.ITALIC, false));
        }

        meta.lore(loreComponents);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
