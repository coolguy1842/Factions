package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import coolguy1842.factions.Util.FactionsMessaging;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;

public class PlayerChat implements Listener {
    @EventHandler
    private void onPlayerChat(AsyncChatEvent e) {
        e.setCancelled(true);

        FactionsMessaging.broadcastMessage(e.getPlayer().displayName(), Component.text(": "), e.message());
    }
}
