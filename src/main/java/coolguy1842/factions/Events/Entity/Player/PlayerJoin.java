package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerJoin implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        if(e == null) return;
        
        Player p = e.getPlayer();

        // create faction player if unavailable
        if(!FactionsManager.getInstance().playerManager.hasPlayer(p.getUniqueId())) {
            FactionsManager.getInstance().playerManager.createPlayer(p, 100L);
        }

        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);

        player.formatName();

        
        String username = PlainTextComponentSerializer.plainText().serialize(p.displayName());
        String avatar = "https://crafatar.com/avatars/" + e.getPlayer().getUniqueId();

        FactionsMessaging.sendToDiscord(username + " joined the game", "Server", avatar);
    }
}
