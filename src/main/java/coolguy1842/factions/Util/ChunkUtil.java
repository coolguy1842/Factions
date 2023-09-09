package coolguy1842.factions.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import coolguy1842.factions.Classes.FactionClaim;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;

public class ChunkUtil {
    private static Boolean playerCanDoInChunk(FactionPlayer player, FactionClaim claim) {
        if(claim == null) return true;
        else if(player == null) return false;
        else if(player.getFaction().getID().equals(claim.getFaction().getID())) return true;
        else if(player.getFaction().allies.contains(claim.getFaction())) return true;
        else if(claim.getFaction().hasOnlinePlayer()) return true;

        return false;
    } 

    public static Boolean playerCanAttackInChunk(Player p, Location blockLocation) {
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);
        FactionClaim claim = FactionsManager.getInstance().claimManager.getClaim(blockLocation.getChunk());

        if(playerCanDoInChunk(player, claim)) return true;

        return false;
    }
    
    public static Boolean playerCanBlockInteractInChunk(Player p, Location blockLocation) {
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);
        FactionClaim claim = FactionsManager.getInstance().claimManager.getClaim(blockLocation.getChunk());

        if(playerCanDoInChunk(player, claim)) return true;

        return false;
    }
    
    public static Boolean playerCanEntityInteractInChunk(Player p, Location blockLocation) {
        FactionPlayer player = FactionsManager.getInstance().playerManager.getPlayer(p);
        FactionClaim claim = FactionsManager.getInstance().claimManager.getClaim(blockLocation.getChunk());

        if(playerCanDoInChunk(player, claim)) return true;

        return false;
    }
}
