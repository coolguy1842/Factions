package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import coolguy1842.factions.Classes.TPARequest;
import coolguy1842.factions.Managers.TPAManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerLeave implements Listener {
    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        
        TPARequest request = TPAManager.getInstance().getPlayerRequest(p);
        if(request != null) TPAManager.getInstance().removePlayerRequest(request);

        String username = PlainTextComponentSerializer.plainText().serialize(p.displayName());
        String avatar = "https://crafatar.com/avatars/" + p.getUniqueId();

        e.quitMessage(p.displayName().append(Component.text(" left the game")));
        FactionsMessaging.sendToDiscord(username + " left the game", "Server", avatar);
    }
}
