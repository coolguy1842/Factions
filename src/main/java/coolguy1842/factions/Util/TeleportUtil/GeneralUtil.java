// taken and modified from https://github.com/TheMrJezza/HorseTpWithMe

package coolguy1842.factions.Util.TeleportUtil;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Util.FactionsLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GeneralUtil {

    // used to run a lambda with minimal boilerplate
    public static void runTask(
            @NotNull Runnable runnable, long delay
    ) {
        Bukkit.getScheduler().runTaskLater(Globals.plugin, runnable, delay);
    }

    // same as above, just async
    public static void runAsync(@NotNull Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Globals.plugin, runnable, delay);
    }

    // true if the specified player DOES NOT have the specified 'horsetpwithme.' permission
    public static boolean denied(
            @NotNull Player player, @NotNull String perm
    ) {
        return !has(player, perm);
    }

    // true if the specified player DOES have the specified 'horsetpwithme.' permission
    public static boolean has(
            @NotNull Player player, @NotNull String perm
    ) {
        return player.hasPermission("horsetpwithme." + perm);
    }

    // check if a player has the 'consent revoked' flag
    public static boolean hasFlag(
            @NotNull Player player, @NotNull NamespacedKey flagKey
    ) {
        return player.getPersistentDataContainer().has(flagKey, PersistentDataType.BYTE);
    }

    // set the 'consent revoked' flag for a player
    public static void setFlag(
            @NotNull Player player, @NotNull NamespacedKey flagKey, boolean value
    ) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (value) pdc.set(flagKey, PersistentDataType.BYTE, (byte) 0);
        else pdc.remove(flagKey);
    }

    public static void validateAll(@NotNull Map<UUID, Entity> map, @NotNull World world) {
        for (Entity entity : world.getEntities()) {
            for (var entry : map.entrySet()) {
                if (entry.getValue().isValid()) continue;
                if (entry.getKey().equals(entity.getUniqueId())) map.put(entry.getKey(), entity);
            }
        }

        map.replaceAll((k, v) -> {
            if (v.isValid()) return v;
            else return null;
        });
    }

    public static Map<UUID, Entity> findEntities(@NotNull World search, Entity... entities) {
        Map<UUID, Entity> map = new HashMap<>(entities.length);
        for (Entity entity : search.getEntities()) {
            for (Entity base : entities) {
                if (base.getUniqueId().equals(entity.getUniqueId())) map.put(base.getUniqueId(), entity);
            }
        }
        return map;
    }

    public static void setExecutor(@NotNull String command, @NotNull CommandExecutor executor) {
        PluginCommand cmd = Globals.plugin.getCommand(command);
        if (cmd != null) cmd.setExecutor(executor);
        else FactionsLogger.warning(String.format("Tried to assign executor to an invalid command! /%s", command));
    }
}