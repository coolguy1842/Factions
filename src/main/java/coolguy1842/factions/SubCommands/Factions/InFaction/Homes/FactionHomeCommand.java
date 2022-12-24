package coolguy1842.factions.SubCommands.Factions.InFaction.Homes;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.Util.FactionsMessaging;
import coolguy1842.factions.Util.PlayerUtil;
import net.kyori.adventure.text.Component;

enum HomeCommandMessages {
    NOTINFACTION,
    NOTEXISTS,
    SUCCESS
}

public class FactionHomeCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        Component.text("That home does not exist."),
        Component.text("Teleporting you to the home \""),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[HomeCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        
        String homeName = "home";
        if(args.length > 1) homeName = args[1];
        
        if(!player.getFaction().hasHome(homeName)) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[HomeCommandMessages.NOTEXISTS.ordinal()]);
            return;
        }

        FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[HomeCommandMessages.SUCCESS.ordinal()], Component.text(homeName + "\"."));
        PlayerUtil.teleportPlayer(p, player.getFaction().getHome(homeName).getLocation());
    }
}
