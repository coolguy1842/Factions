package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Get;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankSetDefaultCommandMessages {
    NOARGS,
    NOTEXISTS
}

public class RankGetDefaultCommand {
    private static Component[] commandMessages = {
        Component.text("You must specify the rank."),
        Component.text("That rank does not exist."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(args.length <= 3) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetDefaultCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasRank(args[3])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetDefaultCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        FactionsMessaging.sendMessage(p, Globals.factionsPrefix,
                                            Component.text("Rank \"" + args[3] + "\" is" + (player.getFaction().getRank(args[3]).getIsDefault() ? "" : " not") + " the default."));
    }
}
