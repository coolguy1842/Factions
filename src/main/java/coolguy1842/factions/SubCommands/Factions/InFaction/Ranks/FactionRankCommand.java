package coolguy1842.factions.SubCommands.Factions.InFaction.Ranks;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Assigning.RankAssignCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Assigning.RankUnAssignCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Get.RankGetCommand;
import coolguy1842.factions.SubCommands.Factions.InFaction.Ranks.Set.RankSetCommand;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RankCommandMessages {
    NOTINFACTION,
    WRONGARG
}

public class FactionRankCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("Invalid option.")
    }; 

    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            RankInfoCommand.execute(p, player, args);
            return;
        }

        switch(args[1]) {
        case "info":
            RankInfoCommand.execute(p, player, args);
            break;
        case "set":
            RankSetCommand.execute(p, player, args);
            break;
        case "get":
            RankGetCommand.execute(p, player, args);
            break;
        case "create":
            RankCreateCommand.execute(p, player, args);
            break;
        case "assign":
            RankAssignCommand.execute(p, player, args);
            break;
        case "unassign":
            RankUnAssignCommand.execute(p, player, args);
            break;
        case "remove":
            RankRemoveCommand.execute(p, player, args);
            break;
        case "rename":
            RankRenameCommand.execute(p, player, args);
            break;
        case "list":
            RankListCommand.execute(p, player, args);
            break;
        default:    
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RankCommandMessages.WRONGARG.ordinal()]);
            break;
        }

    }
}
