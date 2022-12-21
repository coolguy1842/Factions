package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import coolguy1842.factions.Managers.FactionsManager;

public class PlayerJoin implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        if(e == null) return;
        
        Player p = e.getPlayer();

        // create faction player if unavailable
        if(!FactionsManager.getInstance().playerManager.hasPlayer(p.getUniqueId())) {
            FactionsManager.getInstance().playerManager.createPlayer(p, 100L);
        }
    }
}
