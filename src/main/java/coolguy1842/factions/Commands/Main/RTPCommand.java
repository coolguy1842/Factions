package coolguy1842.factions.Commands.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;

public class RTPCommand implements CommandExecutor {
    public static ArrayList<UUID> rtpingPlayers;
    public static HashMap<UUID, Long> rtpCooldowns;
    public static HashMap<UUID, Long> rtpTimes;

    public RTPCommand() {
        rtpingPlayers = new ArrayList<>();
        rtpCooldowns = new HashMap<>();
        rtpTimes = new HashMap<>();
    }

    public static Location searchForTPLocation(Chunk chunk) {
        ChunkSnapshot snapshot = chunk.getChunkSnapshot(true, false, false);

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int y = snapshot.getHighestBlockYAt(x, z);
                
                if(snapshot.getBlockType(x, y, z).isOccluding()) {
                    long chunkX = chunk.getX() << 4;
                    long chunkZ = chunk.getZ() << 4;

                    return new Location(chunk.getWorld(), chunkX + x + 0.5d, y + 1, chunkZ + z + 0.5d);
                }
            }
        }
        
        return null;
    }

    public static void teleportToRandom(Player p) {
        Location pLoc = p.getLocation();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // 10k max in + and - directions with 1k min distance
        long xMod = random.nextInt(20000 - 1000) - 10000;
        xMod += 1000 * (xMod < 0 ? -1 : 1);
        
        long zMod = random.nextInt(20000 - 1000) - 10000;
        zMod += 1000 * (zMod < 0 ? -1 : 1);

        long x = (long)Math.floor(pLoc.getX()) + xMod;
        long z = (long)Math.floor(pLoc.getZ()) + zMod;

        p.getWorld().getChunkAtAsyncUrgently((int)(x >> 4), (int)(z >> 4)).thenAccept(c -> {
            Location loc = searchForTPLocation(c);
            
            if(loc == null) {
                teleportToRandom(p);
                return;
            }

            PlayerUtil.teleportPlayer(p, loc);
            rtpingPlayers.remove(p.getUniqueId());

            FactionsMessaging.sendMessage(p, Component.text(String.format("RTP for %s took %dms", p.getName(), System.currentTimeMillis() - rtpTimes.get(p.getUniqueId()))));
            rtpTimes.remove(p.getUniqueId());
            // rtpCooldowns.put(p, System.currentTimeMillis() + 10000);
            
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
        
            if(rtpingPlayers.contains(p.getUniqueId())) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("You are already teleporting."));
                return true;
            }
            else if(rtpCooldowns.containsKey(p.getUniqueId())) {
                Long millis = rtpCooldowns.get(p.getUniqueId());
                Long curMillis = System.currentTimeMillis();
                if(millis > curMillis) {
                    FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("You must still wait " + ((double)(millis - curMillis) / 1000.0) + " seconds."));
                    return true;
                }
            }

            
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, Component.text("Teleporting you to a random location... Please wait."));

            rtpingPlayers.add(p.getUniqueId());
            rtpTimes.put(p.getUniqueId(), System.currentTimeMillis());
            Bukkit.getScheduler().runTaskAsynchronously(Globals.plugin, () -> teleportToRandom(p));

            return true;
        }
        
        return false;
    }
}