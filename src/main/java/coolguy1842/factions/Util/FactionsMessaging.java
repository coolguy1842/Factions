package coolguy1842.factions.Util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

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

    public static void broadcastMessage(Component... components) {
        Component msg = Component.empty();
        for (Component component : components) {
            msg = msg.append(component);
        }
        
        Bukkit.broadcast(msg);
    }
}
