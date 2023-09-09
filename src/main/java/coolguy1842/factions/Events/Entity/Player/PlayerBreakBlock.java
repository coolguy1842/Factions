package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlayerBreakBlock implements Listener {
    @EventHandler
    private void onPlayerBreakBlock(BlockBreakEvent e) {
        if(e == null) return;
        if(e.getPlayer() == null) return;


        if(e.getBlock().getType() == Material.SPAWNER) {
            if(!e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
            
            Block block = e.getBlock();
            CreatureSpawner spawner = (CreatureSpawner)block.getState();
            if(spawner.getSpawnedType() == null || spawner.getSpawnedType().name() == null) return;

            e.setExpToDrop(0);

            ItemStack item = new ItemStack(Material.SPAWNER);
            BlockStateMeta bsm = (BlockStateMeta)item.getItemMeta();
            CreatureSpawner cs = (CreatureSpawner)bsm.getBlockState();

            cs.setSpawnedType(spawner.getSpawnedType());
            cs.setSpawnCount(spawner.getSpawnCount());
            cs.setSpawnRange(spawner.getSpawnRange());
            cs.setMaxSpawnDelay(spawner.getMaxSpawnDelay());
            cs.setMinSpawnDelay(spawner.getMinSpawnDelay());
            cs.setRequiredPlayerRange(spawner.getRequiredPlayerRange());
            cs.setDelay(spawner.getDelay());

            bsm.setBlockState(cs);

            item.setItemMeta(bsm);
            
            block.setType(Material.AIR);
            e.getPlayer().getWorld().dropItemNaturally(block.getLocation(), item);
        }
    }
}
