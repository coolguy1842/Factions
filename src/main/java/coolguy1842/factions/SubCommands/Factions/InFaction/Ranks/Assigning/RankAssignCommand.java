package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Assigning;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;

enum RankAssignCommandMessages {
    NOPERMISSIONS,
    NORANKARG,
    RANKNOTEXISTS,
    NOPLAYERARG,
    PLAYERNOTEXISTS,
    PLAYERHASRANK,
    SUCCESS
}

public class RankAssignCommand {
    private static Component[] commandMessages = {
        Component.text("You cannot assign ranks."),
        Component.text("You must specify the ranks name."),
        Component.text("That rank does not exist."),
        Component.text("You must specify the players name."),
        Component.text("That player is not in your faction."),
        Component.text("That player already has that rank."),
        Component.text(" has set the rank of "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankAssignCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankAssignCommandMessages.NORANKARG.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasRank(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankAssignCommandMessages.RANKNOTEXISTS.ordinal()]);
            return;
        }
        
        if(args.length <= 3) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankAssignCommandMessages.NOPLAYERARG.ordinal()]);
            return;
        }
        
        UUID assignedUUID = PlayerUtil.getPlayerUUID(args[3]);
        if(!player.getFaction().hasPlayer(assignedUUID)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankAssignCommandMessages.PLAYERNOTEXISTS.ordinal()]);
            return;
        }

        FactionPlayer assignedPlayer = FactionsManager.getInstance().playerManager.getPlayer(assignedUUID);
        if(assignedPlayer.hasRank()) {
            if(assignedPlayer.getRank().getDisplayName().equals(args[2])) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankAssignCommandMessages.PLAYERHASRANK.ordinal()]);
                return;
            }
        }


        assignedPlayer.setRank(player.getFaction().getRank(args[2]).getID());
        player.getFaction().broadcastMessage(Globals.factionsPrefix, 
                                                                    p.displayName(), 
                                                                    commandMessages[RankAssignCommandMessages.SUCCESS.ordinal()], 
                                                                    assignedPlayer.getDisplayName(), 
                                                                    Component.text(" to \"" + args[2] + "\"."));
    }
}
