package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Classes.FactionRank;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankRemoveCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    NOTEXISTS,
    SUCCESS
}

public class RankRemoveCommand {
    private static Component[] commandMessages = {
        Component.text("You cannot remove ranks."),
        Component.text("You must specify the rank to remove."),
        Component.text("That rank does not exist."),
        Component.text(" has removed the rank \"")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRemoveCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRemoveCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasRank(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankRemoveCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        FactionRank rank = player.getFaction().getRank(args[2]);
        FactionsManager.getInstance().rankManager.deleteRank(rank);

        player.getFaction().broadcastMessage(Globals.factionsPrefix,
                                            p.name(),
                                            commandMessages[RankRemoveCommandMessages.SUCCESS.ordinal()],
                                            Component.text(rank.getDisplayName() + "\"."));

    }
}
