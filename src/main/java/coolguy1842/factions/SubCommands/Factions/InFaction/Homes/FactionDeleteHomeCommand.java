package coolguy1842.factions.SubCommands.Factions.InFaction.Homes;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum DeleteHomeCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    NOARGS,
    NOTEXISTS,
    SUCCESS
}

public class FactionDeleteHomeCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to delete homes."),
        Component.text("You must specify a home."),
        Component.text("That home does not exist."),
        Component.text(" deleted the home \""),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeleteHomeCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.hasPermission("delhome")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeleteHomeCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeleteHomeCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasHome(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[DeleteHomeCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        FactionsManager.getInstance().homeManager.deleteHome(player.getFaction().getHome(args[1]).getID());
        player.getFaction().broadcastMessage( Globals.factionsPrefix, p.displayName(), commandMessages[DeleteHomeCommandMessages.SUCCESS.ordinal()], Component.text(args[1] + "\"."));
    }
}
