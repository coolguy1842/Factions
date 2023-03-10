package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import coolguy1842.factions.Util.FactionsMessaging;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerChat implements Listener {
    @EventHandler
    private void onPlayerChat(AsyncChatEvent e) {
        e.setCancelled(true);

        FactionsMessaging.broadcastMessage(null, e.getPlayer().displayName(), Component.text(": "), e.message());

        String contents = PlainTextComponentSerializer.plainText().serialize(e.message());
        String username = PlainTextComponentSerializer.plainText().serialize(e.getPlayer().displayName());
        String avatar = "https://crafatar.com/avatars/" + e.getPlayer().getUniqueId();

        FactionsMessaging.sendToDiscord(contents, username, avatar);
    }
}
