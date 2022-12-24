package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Get;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankGetPermissionCommandMessages {
    NORANK,
    RANKNOTEXISTS,

    NOPERMISSION,
    PERMISSIONNOTEXISTS
}

public class RankGetPermission {
    private static Component[] commandMessages = {
        Component.text("You must specify the rank."),
        Component.text("That rank does not exist."),
        
        Component.text("You must specify the permission"),
        Component.text("That permission does not exist")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(args.length <= 3) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankGetPermissionCommandMessages.NORANK.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasRank(args[3])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankGetPermissionCommandMessages.RANKNOTEXISTS.ordinal()]);
            return;
        }

        if(args.length <= 4) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankGetPermissionCommandMessages.NOPERMISSION.ordinal()]);
            return;
        }
        else if(!Globals.rankPermissions.contains(args[4])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankGetPermissionCommandMessages.PERMISSIONNOTEXISTS.ordinal()]);
            return;
        }

        FactionRank rank = player.getFaction().getRank(args[3]);
        String permission = args[4];

        FactionsMessaging.sendMessage(p, Globals.factionsPrefix,
                                            Component.text("Permission \"" + permission + "\" of \"" + rank.getDisplayName() + "\" = " + (rank.hasPermission(permission) ? "true" : "false")));
    }
}
