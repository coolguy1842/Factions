package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Set;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankSetCommandMessages {
    NOPERMISSIONS,
    NOARGS,
    NOTEXISTS
}

public class RankSetCommand {
    private static Component[] commandMessages = {
        Component.text("You cannot modify ranks."),
        Component.text("You must specify the option."),
        Component.text("That option does not exist.")
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.isLeader()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetCommandMessages.NOARGS.ordinal()]);
            return;
        }

        switch(args[2]) {
        case "default":
            RankSetDefaultCommand.execute(p, player, args);
            break;
        case "permission":
            RankSetPermission.execute(p, player, args);
            break;
        default: 
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankSetCommandMessages.NOTEXISTS.ordinal()]);
            break;
        }
    }
}
