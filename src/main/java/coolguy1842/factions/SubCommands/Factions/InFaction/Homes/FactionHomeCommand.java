package coolguy1842.factions.SubCommands.Factions.InFaction.Homes;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;

enum HomeCommandMessages {
    NOTINFACTION,
    NOARGS,
    NOTEXISTS,
    SUCCESS
}

public class FactionHomeCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("You must specify a home."),
        Component.text("That home does not exist."),
        Component.text("Teleporting you to the home "),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[HomeCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[HomeCommandMessages.NOARGS.ordinal()]);
            return;
        }
        else if(!player.getFaction().hasHome(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[HomeCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[HomeCommandMessages.SUCCESS.ordinal()], Component.text(args[1] + "."));
        PlayerUtil.teleportPlayer(p, player.getFaction().getHome(args[1]).getLocation());
    }
}
