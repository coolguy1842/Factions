package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Set;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankSetDefaultCommandMessages {
    NOARGS,
    NOTEXISTS,
    SUCCESS
}

public class RankSetDefaultCommand {
    private static Component[] commandMessages = {
        Component.text("You must specify the rank."),
        Component.text("That rank does not exist."),
        Component.text(" set the default rank to \"")
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

        player.getFaction().setDefaultRank(player.getFaction().getRank(args[3]));
        player.getFaction().broadcastMessage(Globals.factionsPrefix, p.displayName(), commandMessages[RankSetDefaultCommandMessages.SUCCESS.ordinal()], Component.text(args[3] + "\"."));
    }
}
