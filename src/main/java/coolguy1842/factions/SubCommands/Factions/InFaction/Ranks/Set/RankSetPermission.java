package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Set;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankSetPermissionCommandMessages {
    NORANK,
    RANKNOTEXISTS,

    NOPERMISSION,
    PERMISSIONNOTEXISTS,

    NOVALUE,
    VALUENOTEXISTS,

    SUCCESS
}

public class RankSetPermission {
    private static Component[] commandMessages = {
        Component.text("You must specify the rank."),
        Component.text("That rank does not exist."),
        
        Component.text("You must specify the permission"),
        Component.text("That permission does not exist"),
        
        Component.text("You must specify the value"),
        Component.text("That is an invalid value"),

        Component.text(" has set the permission ")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(args.length <= 3) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetPermissionCommandMessages.NORANK.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasRank(args[3])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetPermissionCommandMessages.RANKNOTEXISTS.ordinal()]);
            return;
        }

        if(args.length <= 4) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetPermissionCommandMessages.NOPERMISSION.ordinal()]);
            return;
        }
        else if(!Globals.rankPermissions.contains(args[4])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetPermissionCommandMessages.PERMISSIONNOTEXISTS.ordinal()]);
            return;
        }

        if(args.length <= 5) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetPermissionCommandMessages.NOVALUE.ordinal()]);
            return;
        }
        else if(!args[5].equals("true") && !args[5].equals("false")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetPermissionCommandMessages.VALUENOTEXISTS.ordinal()]);
            return;
        }

        FactionRank rank = player.getFaction().getRank(args[3]);
        String permission = args[4];
        Boolean value = args[5].equals("true");

        if(value) rank.addPermission(permission);
        else rank.removePermission(permission);


        player.getFaction().broadcastMessage(Globals.factionsPrefix,
                                            p.name(),
                                            commandMessages[RankSetPermissionCommandMessages.SUCCESS.ordinal()],
                                            Component.text(permission + " of \"" + rank.getDisplayName() + "\" to " + args[5] + "."));
    }
}
