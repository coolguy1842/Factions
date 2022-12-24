package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankCreateCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    EXISTS,
    SUCCESS
}

public class RankCreateCommand {
    private static Component[] commandMessages = {
        Component.text("You cannot create ranks."),
        Component.text("You must specify the new ranks name."),
        Component.text("That rank already exists."),
        Component.text(" create a rank \""),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankCreateCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankCreateCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(player.getFaction().hasRank(args[2])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankCreateCommandMessages.EXISTS.ordinal()]);
            return;
        }

        FactionsManager.getInstance().rankManager.createRank(UUID.randomUUID(), player.getFaction(), args[2], false);
        player.getFaction().broadcastMessage(Globals.factionsPrefix, p.displayName(), commandMessages[RankCreateCommandMessages.SUCCESS.ordinal()], Component.text(args[2] + "\"."));
    }
}
