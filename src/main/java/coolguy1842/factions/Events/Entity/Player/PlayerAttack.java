package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.ChunkUtil;
import coolguy1842.factions.Util.FactionsMessaging;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.kyori.adventure.text.Component;

public class PlayerAttack implements Listener {
    @EventHandler
    private void onPlayerAttack(PrePlayerAttackEntityEvent e) {
        if(e == null) return;
        Player p = e.getPlayer();

        if(p == null) return;
        
        
        if(!ChunkUtil.playerCanAttackInChunk(p, e.getAttacked().getLocation())) {
            e.setCancelled(true);

            FactionClaim claim = FactionsManager.getInstance().claimManager.getClaim(e.getAttacked().getChunk());

            FactionsMessaging.sendMessage(p, 
                                            Globals.factionsPrefix, 
                                            Component.text("You cannot attack in a claim from "), 
                                            claim.getFaction().getFormattedDisplayName(),
                                            Component.text("."));
        }
    }
}
