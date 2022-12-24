package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankRenameCommandMessages {
    NOPERMISSIONS,
    
    NORANK,
    NOTEXISTS,

    NONEWNAME,
    NAMEEXISTS,

    SUCCESS
}

public class RankRenameCommand {
    private static Component[] commandMessages = {
        Component.text("You cannot rename ranks."),

        Component.text("You must specify the rank to rename."),
        Component.text("That rank does not exist."),

        Component.text("You must specify the new name of the rank."),
        Component.text("A rank with that name already exists."),
        
        Component.text(" has renamed the rank \"")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRenameCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRenameCommandMessages.NORANK.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasRank(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRenameCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }
        else if(args.length <= 3) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRenameCommandMessages.NONEWNAME.ordinal()]);
            return;
        }
        else if(player.getFaction().hasRank(args[3])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRenameCommandMessages.NAMEEXISTS.ordinal()]);
            return;
        }

        player.getFaction().getRank(args[2]).setDisplayName(args[3]);
        player.getFaction().broadcastMessage(Globals.factionsPrefix,
                                            p.displayName(),
                                            commandMessages[RankRenameCommandMessages.SUCCESS.ordinal()],
                                            Component.text(args[2] + "\" to \"" + args[3] + "\"."));
    }
}
