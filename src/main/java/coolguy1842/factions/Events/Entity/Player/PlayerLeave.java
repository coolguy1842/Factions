package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import coolguy1842.factions.Classes.TPARequest;
import coolguy1842.factions.Managers.TPAManager;

public class PlayerLeave implements Listener {
    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        
        TPARequest request = TPAManager.getInstance().getPlayerRequest(player);
        if(request != null) TPAManager.getInstance().removePlayerRequest(request);
    }
}
