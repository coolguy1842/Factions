package coolguy1842.factions.Commands.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

public class RTPCommand implements CommandExecutor {
    public static ArrayList<Player> rtpingPlayers;
    public static HashMap<Player, Long> rtpCooldowns;

    public RTPCommand() {
        rtpingPlayers = new ArrayList<>();
        rtpCooldowns = new HashMap<>();
    }

    public static Location searchForTPLocation(Chunk chunk) {
        ChunkSnapshot snapshot = chunk.getChunkSnapshot();

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int y = snapshot.getHighestBlockYAt(x, z);
                BlockData data = snapshot.getBlockData(x, y, z);
                BlockData data2 = snapshot.getBlockData(x, y + 1, z);
                BlockData data3 = snapshot.getBlockData(x, y + 2, z);
                
                Material block = data.getMaterial();
                
                if(block.isSolid() && block.isCollidable() && !block.isEmpty() && block.isOccluding() && data2.getMaterial().isAir() && data3.getMaterial().isAir()) {
                    return new Location(chunk.getWorld(), (chunk.getX() << 4) + x, y + 2, (chunk.getZ() << 4) + z);
                }
            }
        }
         
        return null;
    }

    public static void teleportToRandom(Player p) {
        Location pLoc = p.getLocation();

        int x = (int)pLoc.getX() + (1000 + ThreadLocalRandom.current().nextInt(10000 - 1000 + 1));
        int z = (int)pLoc.getZ() + (1000 + ThreadLocalRandom.current().nextInt(10000 - 1000 + 1));

        p.getWorld().getChunkAtAsync(x >> 4, z >> 4).thenAccept(c -> {
            Location loc = searchForTPLocation(c);
            
            if(loc == null) {
                teleportToRandom(p);
                return;
            }

            p.teleportAsync(loc);
            rtpingPlayers.remove(p);
            rtpCooldowns.put(p, System.currentTimeMillis() + 10000);
            
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("Teleported you to a random location."));
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;      
        
            if(rtpingPlayers.contains(p)) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("You are already teleporting."));
                return true;
            }
            else if(rtpCooldowns.containsKey(p)) {
                Long millis = rtpCooldowns.get(p);
                Long curMillis = System.currentTimeMillis();
                if(millis > curMillis) {
                    FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("You must still wait " + ((double)(millis - curMillis) / 1000.0) + " seconds."));
                    return true;
                }
            }

            
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("Teleporting you to a random location... Please wait."));

            rtpingPlayers.add(p);
            Bukkit.getScheduler().runTaskAsynchronously(Globals.plugin, () -> teleportToRandom(p));

            return true;
        }
        
        return false;
    }
}