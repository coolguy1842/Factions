package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerDeath implements Listener {
    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        String avatar = "https://crafatar.com/avatars/" + p.getUniqueId();
        
        String contents = PlainTextComponentSerializer.plainText().serialize(p.displayName()) + " ";

        String[] splitStr = PlainTextComponentSerializer.plainText().serialize(e.deathMessage()).split(" ");
        for(int i = 2; i < splitStr.length; i++) contents += splitStr[i] + " ";
        
        FactionsMessaging.sendToDiscord(contents, "Server", avatar);
    }
}
