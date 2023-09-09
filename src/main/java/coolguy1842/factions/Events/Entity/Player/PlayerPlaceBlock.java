package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlayerPlaceBlock implements Listener {
    @EventHandler
    private void onPlayerPlaceBlock(BlockPlaceEvent e) {
        if(e == null || e.getPlayer() == null) return;

        if(e.getBlock().getType() == Material.SPAWNER) {
            Block block = e.getBlockPlaced();
            CreatureSpawner spawner = (CreatureSpawner)block.getState();

            ItemStack usedItem = e.getItemInHand();
            BlockStateMeta bsm = (BlockStateMeta)usedItem.getItemMeta();
            CreatureSpawner cs = (CreatureSpawner)bsm.getBlockState();

            spawner.setSpawnedType(cs.getSpawnedType());
            spawner.setSpawnCount(cs.getSpawnCount());
            spawner.setSpawnRange(cs.getSpawnRange());
            spawner.setMaxSpawnDelay(cs.getMaxSpawnDelay());
            spawner.setMinSpawnDelay(cs.getMinSpawnDelay());
            spawner.setRequiredPlayerRange(cs.getRequiredPlayerRange());
            spawner.setDelay(cs.getDelay());

            spawner.update();
        }
    }
}
