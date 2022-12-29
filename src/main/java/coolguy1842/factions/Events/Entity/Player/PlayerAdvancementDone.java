package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerAdvancementDone implements Listener {
    @EventHandler
    private void onPlayerAdvancementDone(PlayerAdvancementDoneEvent e) {
        Player p = e.getPlayer();

        String contents = PlainTextComponentSerializer.plainText().serialize(p.displayName()) + " ";

        String[] splitStr = PlainTextComponentSerializer.plainText().serialize(e.message()).split(" ");
        for(int i = 2; i < splitStr.length; i++) contents += splitStr[i] + " ";

        String avatar = "https://crafatar.com/avatars/" + e.getPlayer().getUniqueId();

        
        FactionsMessaging.sendToDiscord(contents, "Server", avatar);
    }
}
