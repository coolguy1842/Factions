package coolguy1842.factions.SubCommands.Factions.InFaction.Homes;

import java.util.UUID;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionHome;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Managers.FactionsManager;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum SetHomeCommandMessages {
    NOTINFACTION,
    NOPERMISSIONS,
    NOARGS,
    SUCCESSMODIFIED,
    SUCCESSCREATED
}

public class FactionSetHomeCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to set homes."),
        Component.text("You must specify a home."),
        Component.text(" modified the home called "),
        Component.text(" created a home called "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetHomeCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(!player.hasPermission("sethome")) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetHomeCommandMessages.NOPERMISSIONS.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetHomeCommandMessages.NOARGS.ordinal()]);
            return;
        }

        FactionHome home = player.getFaction().getHome(args[1]);

        if(home != null) {
            home.setLocation(p.getLocation());
            
            player.getFaction().broadcastMessage(Globals.factionsPrefix, p.displayName(), commandMessages[SetHomeCommandMessages.SUCCESSMODIFIED.ordinal()], Component.text(args[1]));
        }
        else {
            FactionsManager.getInstance().homeManager.createHome(UUID.randomUUID(), args[1], p.getLocation(), player.getFaction());
            player.getFaction().broadcastMessage(Globals.factionsPrefix, p.displayName(), commandMessages[SetHomeCommandMessages.SUCCESSCREATED.ordinal()], Component.text(args[1]));
        }
    }
}
