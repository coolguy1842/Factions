package coolguy1842.factions.Events.Entity.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
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

        if(player.isLeader()) {
            for(Faction inviter : player.getFaction().allyInvites) {
                Component[] message = { Globals.factionsPrefix, 
                    Component.text("\""),
                    inviter.getFormattedDisplayName(),
                    Component.text("\" wants be allies.")};

                Component[] commandMessage = {
                Component.text("[Accept] ")
                                            .color(TextColor.color(0, 255, 0))
                                            .clickEvent(ClickEvent.runCommand("/f ally " + inviter.getDisplayName()))
                                            .hoverEvent(
                                                HoverEvent.showText(Component.text("Accept \"")
                                                                    .append(inviter.getFormattedDisplayName())
                                                                    .append(Component.text("\" to be your allies?")))
                                            ),
                Component.text("[Decline]")
                                            .color(TextColor.color(255, 0, 0))
                                            .clickEvent(ClickEvent.runCommand("/f declineally " + inviter.getDisplayName()))
                                            .hoverEvent(
                                                HoverEvent.showText(Component.text("Decline \"")
                                                                    .append(inviter.getFormattedDisplayName())
                                                                    .append(Component.text("\" from being your allies?"))
                                            ))
                };

                FactionsMessaging.sendMessage(p, message);
                FactionsMessaging.sendMessage(p, commandMessage);
            }
        }
        
        String username = PlainTextComponentSerializer.plainText().serialize(p.displayName());
        String avatar = "https://crafatar.com/avatars/" + e.getPlayer().getUniqueId();

        FactionsMessaging.sendToDiscord(username + " joined the game", "Server", avatar);
    }
}
