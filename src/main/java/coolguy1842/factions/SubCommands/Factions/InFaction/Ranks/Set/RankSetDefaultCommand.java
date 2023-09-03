package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Set;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankSetDefaultCommandMessages {
    NOTEXISTS,
    SUCCESS,
    SUCCESSNODEFAULT
}

public class RankSetDefaultCommand {
    private static Component[] commandMessages = {
        Component.text("That rank does not exist."),
        Component.text(" set the default rank to \""),
        Component.text(" removed the default rank.")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        FactionRank rank = null;

        if(args.length > 3) {
            rank = player.getFaction().getRank(args[3]);
         
            if(!player.getFaction().hasRank(args[3])) {
                FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetDefaultCommandMessages.NOTEXISTS.ordinal()]);
                return;
            }
        }

        player.getFaction().setDefaultRank(rank);
        if(rank != null) {
            player.getFaction().broadcastMessage(Globals.factionsPrefix, 
                                                p.name(), 
                                                commandMessages[RankSetDefaultCommandMessages.SUCCESS.ordinal()], 
                                                Component.text(rank.getDisplayName() + "\"."));
        }
        else {
            player.getFaction().broadcastMessage(Globals.factionsPrefix, 
                                                p.name(), 
                                                commandMessages[RankSetDefaultCommandMessages.SUCCESSNODEFAULT.ordinal()]);
        }
    }
}
