package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Assigning;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;

enum RankUnAssignCommandMessages {
    NOPERMISSIONS,
    NOPLAYERARG,
    PLAYERNOTEXISTS,
    PLAYERHASNORANK,
    SUCCESS
}

public class RankUnAssignCommand {
    private static Component[] commandMessages = {
        Component.text("You cannot assign ranks."),
        Component.text("You must specify the players name."),
        Component.text("That player is not in your faction."),
        Component.text("That player already has no rank."),
        Component.text(" has removed rank of "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankUnAssignCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankUnAssignCommandMessages.NOPLAYERARG.ordinal()]);
            return;
        }
        
        UUID assignedUUID = PlayerUtil.getPlayerUUID(args[2]);
        if(!player.getFaction().hasPlayer(assignedUUID)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankUnAssignCommandMessages.PLAYERNOTEXISTS.ordinal()]);
            return;
        }

        FactionPlayer assignedPlayer = FactionsManager.getInstance().playerManager.getPlayer(assignedUUID);
        
        if(!assignedPlayer.hasRank()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankUnAssignCommandMessages.PLAYERHASNORANK.ordinal()]);
            return;
        }

        assignedPlayer.setRank(null);
        player.getFaction().broadcastMessage(Globals.factionsPrefix, 
                                                                    p.displayName(), 
                                                                    commandMessages[RankUnAssignCommandMessages.SUCCESS.ordinal()], 
                                                                    assignedPlayer.getDisplayName(), Component.text("."));
    }
}
