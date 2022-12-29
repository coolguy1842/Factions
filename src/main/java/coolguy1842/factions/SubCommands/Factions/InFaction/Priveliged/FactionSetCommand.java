package coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged;

import org.bukkit.entity.Player;

import coolguy1842.factions.Globals;
import coolguy1842.factions.Classes.FactionPlayer;
import coolguy1842.factions.SubCommands.Factions.InFaction.Priveliged.Set.SetColorCommand;
import coolguy1842.factions.Util.FactionsMessaging;
import net.kyori.adventure.text.Component;

enum SetCommandMessages {
    NOTINFACTION,

    NOOPTION,
    INVALIDOPTION,
    
    NOVALUE,
}

public class FactionSetCommand {
    private static Component[] commandMessages = {
        Component.text("You are not in a faction."),
        
        Component.text("You must specify the option."),
        Component.text("That option does not exist."),
        
        Component.text("You must specify the value."),
    }; 
    
    public static void execute(Player p, FactionPlayer player, String[] args) {
        if(!player.inFaction()) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetCommandMessages.NOTINFACTION.ordinal()]);
            return;
        }
        else if(args.length <= 1) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetCommandMessages.NOOPTION.ordinal()]);
            return;
        }
        else if(!Globals.factionOptions.contains(args[1])) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetCommandMessages.INVALIDOPTION.ordinal()]);
            return;
        }
        else if(args.length <= 2) {
            FactionsMessaging.sendMessage(p, Globals.factionsPrefix, commandMessages[SetCommandMessages.NOVALUE.ordinal()]);
            return;
        }

        switch(args[1]) {
        case "color":
            SetColorCommand.execute(p, player, args);
            break;
        default: break;
        }
    }
}
