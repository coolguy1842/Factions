package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.ChunkUtil;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

public class PlayerInteract implements Listener {
    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if(e == null) return;

        Player p = e.getPlayer();

        if(p == null) return;
        
        Entity entity = e.getRightClicked();
        if(!ChunkUtil.playerCanEntityInteractInChunk(p, entity.getLocation())) {
            e.setCancelled(true);

            Faction chunkFaction = FactionsManager.getInstance().claimManager.getClaim(entity.getChunk());

            
        if(e.getHand().equals(EquipmentSlot.HAND)) FactionsMessaging.sendMessage(p, 
                                            Globals.factionsPrefix, 
                                            Component.text("You cannot interact with entities in a claim from "), 
                                            chunkFaction.getFormattedDisplayName(),
                                            Component.text("."));
        }
    } 

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent e) {
        if(e == null) return;
        else if(e.getClickedBlock() == null) return;
        
        Player p = e.getPlayer();
        if(p == null) return;
        if(e.getHand() == EquipmentSlot.OFF_HAND && (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR)) return;
        
        
        Block block = e.getClickedBlock();
        if(!ChunkUtil.playerCanBlockInteractInChunk(p, block.getLocation())) {
            e.setCancelled(true);

            Faction chunkFaction = FactionsManager.getInstance().claimManager.getClaim(block.getChunk());

            FactionsMessaging.sendMessage(p, 
                                            Globals.factionsPrefix, 
                                            Component.text("You cannot interact with blocks in a claim from "), 
                                            chunkFaction.getFormattedDisplayName(),
                                            Component.text("."));
        }
    } 
}
