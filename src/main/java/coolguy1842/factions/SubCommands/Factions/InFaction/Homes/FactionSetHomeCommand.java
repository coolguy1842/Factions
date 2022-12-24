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
    SUCCESSMODIFIED,
    SUCCESSCREATED
}

public class FactionSetHomeCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You do not have the permissions to set homes."),
        Component.text(" modified the home \""),
        Component.text(" created a home \""),
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
        
        String homeName = "home";
        if(args.length > 1) homeName = args[1];

        FactionHome home = player.getFaction().getHome(homeName);

        if(home != null) {
            home.setLocation(p.getLocation());
            
            player.getFaction().broadcastMessage(Globals.factionsPrefix, p.displayName(), commandMessages[SetHomeCommandMessages.SUCCESSMODIFIED.ordinal()], Component.text(homeName + "\"."));
        }
        else {
            FactionsManager.getInstance().homeManager.createHome(UUID.randomUUID(), homeName, p.getLocation(), player.getFaction());
            player.getFaction().broadcastMessage(Globals.factionsPrefix, p.displayName(), commandMessages[SetHomeCommandMessages.SUCCESSCREATED.ordinal()], Component.text(homeName + "\"."));
        }
    }
}
