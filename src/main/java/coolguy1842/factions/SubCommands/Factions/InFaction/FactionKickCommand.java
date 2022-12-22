package coolguy1842.factions.SubCommands.Factions.InFaction;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum KickCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    NOARGS,
    SELF,
    WRONGPLAYER,
    SUCCESS
}

public class FactionKickCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions."),
        Component.text("You must specify the player to kick."),
        Component.text("You cannot kick yourself."),
        Component.text("You cannot kick this player."),
        Component.text(" has been kicked from "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.hasPermission("kick")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.NOARGS.ordinal()]);
            return;
        }

        UUID kickeeID;
        Player kickeeP = Bukkit.getPlayerExact(args[1]);
        Component kickeeDisplayName;
        if(kickeeP == null) {
            OfflinePlayer kickeeOP = Bukkit.getOfflinePlayer(args[1]);
            if(kickeeOP == null) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[KickCommandMessages.WRONGPLAYER.ordinal()]);
                return;
            }

            kickeeID = kickeeOP.getUniqueId();
            kickeeDisplayName = Component.text(kickeeOP.getName());
        }
        else {
            kickeeID = kickeeP.getUniqueId();
            kickeeDisplayName = kickeeP.displayName();
        }

        FactionPlayer kickee = FactionsManager.getInstance().playerManager.getPlayer(kickeeID);
        
        kickee.setRank(null);
        kickee.setFaction(null);
    
        for(FactionPlayer factionPlayer : player.getFaction().players.values()) {
            Player fPlayer = factionPlayer.getPlayer();
            
            FactionsMessaging.sendMessage(fPlayer, Globals.factionsPrefix, 
                                                        kickeeDisplayName, 
                                                        commandMessages[KickCommandMessages.SUCCESS.ordinal()],
                                                        player.getFaction().getFormattedDisplayName(),
                                                        Component.text("."));
        }
    }
}
