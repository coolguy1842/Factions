package coolguy1842.factions.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.Faction;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class ChunkUtil {
    private static Boolean playerCanDoInChunk(FactionPlayer player, Faction chunkFaction) {
        if(chunkFaction == null) return true;
        else if(player.getFaction().getID().equals(chunkFaction.getID())) return true;
        else if(chunkFaction.hasOnlinePlayer()) return true;

        return false;
    } 

    public static Boolean playerCanAttackInChunk(Player p, Location blockLocation) {
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);
        Faction chunkFaction = FactionsManager.getInstance().claimManager.getClaim(blockLocation.getChunk());

        if(playerCanDoInChunk(player, chunkFaction)) return true;

        return false;
    }
    
    public static Boolean playerCanBlockInteractInChunk(Player p, Location blockLocation) {
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);
        Faction chunkFaction = FactionsManager.getInstance().claimManager.getClaim(blockLocation.getChunk());

        if(playerCanDoInChunk(player, chunkFaction)) return true;

        return false;
    }
    
    public static Boolean playerCanEntityInteractInChunk(Player p, Location blockLocation) {
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);
        Faction chunkFaction = FactionsManager.getInstance().claimManager.getClaim(blockLocation.getChunk());

        if(playerCanDoInChunk(player, chunkFaction)) return true;

        return false;
    }
}
