package coolguy1842.factions.Commands.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
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
        long chunkX = chunk.getX() << 4;
        long chunkZ = chunk.getZ() << 4;

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int y = snapshot.getHighestBlockYAt(x, z);
                Material block = snapshot.getBlockType(x, y, z);
                Material playerBlock = snapshot.getBlockType(x, y + 1, z);
                Material playerBlock2 = snapshot.getBlockType(x, y + 2, z);
                
                if(block.isSolid() && block.isCollidable() && !block.isEmpty() && block.isOccluding() 
                    && playerBlock.isEmpty() && playerBlock2.isEmpty()) {
                    return new Location(chunk.getWorld(), chunkX + x + 0.5d, y + 1, chunkZ + z + 0.5d);
                }
            }
        }
         
        return null;
    }

    public static void teleportToRandom(Player p) {
        Location pLoc = p.getLocation();

        int x = (int)(int)Math.floor(pLoc.getX()) + (1000 + ThreadLocalRandom.current().nextInt(10000 - 1000 + 1));
        int z = (int)(int)Math.floor(pLoc.getZ()) + (1000 + ThreadLocalRandom.current().nextInt(10000 - 1000 + 1));

        p.getWorld().getChunkAtAsyncUrgently(x >> 4, z >> 4).thenAccept(c -> {
            Location loc = searchForTPLocation(c);
            
            if(loc == null) {
                teleportToRandom(p);
                return;
            }

            PlayerUtil.teleportPlayer(p, loc);

            rtpingPlayers.remove(p);
            rtpCooldowns.put(p, System.currentTimeMillis() + 10000);
            
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text(String.format("Teleported you to a random location(%d,%d,%d).", 
                                                                                        Math.round(loc.getX()),
                                                                                        Math.round(loc.getY()),
                                                                                        Math.round(loc.getZ()))));
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