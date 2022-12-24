package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum RenameCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    NOARGS,
    EXISTS,
    SUCCESS
}

public class FactionRenameCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to rename this faction."),
        Component.text("You must specify a new name."),
        Component.text("That name is already taken."),
        Component.text("Renamed the faction."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RenameCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.hasPermission("rename")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RenameCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RenameCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(FactionsManager.getInstance().factionManager.hasFaction(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RenameCommandMessages.EXISTS.ordinal()]);
            return;
        }

        player.getFaction().setDisplayName(args[1]);
        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[RenameCommandMessages.SUCCESS.ordinal()]);
    }
}
