package coolguy1842.factions.Util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import coolguy1842.discordrelay.Globals;
import coolguy1842.discordrelay.Util.SendToDiscordEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class FactionsMessaging {
    public static void sendMessage(Player player, Component... components) {
        if(player == null) return;

        Component msg = Component.empty();
        for (Component component : components) {
            msg = msg.append(component);
        }
        
        player.sendMessage(msg);
    }
    
    public static void sendMessage(UUID id, Component... components) {
        if(Bukkit.getPlayer(id) == null) return;
        
        Component msg = Component.empty();
        for (Component component : components) {
            msg = msg.append(component);
        }
     
        Bukkit.getPlayer(id).sendMessage(msg);
    }

    public static void broadcastMessage(Player broadcaster, Component... components) {
        Component msg = Component.empty();
        for (Component component : components) {
            msg = msg.append(component);
        }
        
        Bukkit.broadcast(msg);

        
        if(broadcaster != null) {
            String contents = StringUtil.componentsToString(components);
            String username = PlainTextComponentSerializer.plainText().serialize(broadcaster.getPlayer().displayName());
            String avatar = "https://crafatar.com/avatars/" + broadcaster.getUniqueId();
            
            sendToDiscord(contents, username, avatar);
        }
    }

    public static void sendToDiscord(String contents, String username, String avatar) {
        Bukkit.getScheduler().runTaskAsynchronously(Globals.plugin, () -> Bukkit.getPluginManager().callEvent(new SendToDiscordEvent(username, avatar, contents)));
    }

    public static void sendToDiscord(String username, String avatar, Component... contents) {
        Bukkit.getScheduler().runTaskAsynchronously(Globals.plugin, () -> Bukkit.getPluginManager().callEvent(new SendToDiscordEvent(username, avatar, StringUtil.componentsToString(contents))));
    }
}
